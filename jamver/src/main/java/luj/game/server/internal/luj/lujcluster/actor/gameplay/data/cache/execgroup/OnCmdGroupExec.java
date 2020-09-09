package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execgroup;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import luj.ava.spring.Internal;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.CacheRequest;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.internal.data.command.queue.add.WaitQueueGroupAdder;
import luj.game.server.internal.data.command.queue.element.GroupReqElement;
import luj.game.server.internal.data.execute.group.GroupExecFinisher;
import luj.game.server.internal.data.execute.load.DataLoadRequestMaker;
import luj.game.server.internal.data.execute.load.missing.DataReadyChecker;
import luj.game.server.internal.data.execute.load.request.MissingLoadRequestor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

@Internal
final class OnCmdGroupExec implements GameplayDataActor.Handler<CmdGroupExecMsg> {

  @Override
  public void onHandle(Context ctx) {
    GameplayDataActor actor = ctx.getActorState(this);
    CmdGroupExecMsg msg = ctx.getMessage(this);

    List<Elem> cmdList = msg.commands().stream()
        .map(m -> makeElem(m, actor))
        .collect(Collectors.toList());

    List<CacheRequest> reqList = cmdList.stream()
        .map(c -> c._req)
        .collect(Collectors.toList());

    Map<Class<?>, GameplayDataActor.GroupKit> groupMap = actor.getCmdGroupMap();
    Class<?> groupType = msg.groupType();

    GameplayDataActor.GroupKit groupKit = groupMap.get(groupType);
    checkNotNull(groupKit, groupType.getName());

    List<GroupReqElement> elemList = cmdList.stream()
        .map(c -> new GroupReqElement(c._kit, c._msg.param(), c._req))
        .collect(Collectors.toList());

    CacheContainer dataCache = actor.getDataCache();
    DataReadyChecker.Result readyResult = new DataReadyChecker(reqList, dataCache).check();

    //TODO: 还有网络连接
    ServerMessageHandler.Server remoteRef = msg.remoteRef();

    if (!readyResult.isReady()) {
      // 发起数据读取
      new MissingLoadRequestor(readyResult.getMissingList(),
          dataCache, actor.getLoadRef()).request();

      // 加入等待队列
      new WaitQueueGroupAdder(actor.getCommandQueue(),
          groupKit.getGroup(), elemList, remoteRef).addGroup();

      return;
    }

    new GroupExecFinisher(groupKit.getGroup(), elemList, dataCache,
        ctx.getActorRef(), actor.getSaveRef(), remoteRef, actor.getLujbean()).finish();
  }

  private Elem makeElem(CmdGroupExecMsg.Command msg, GameplayDataActor actor) {
    Elem elem = new Elem();
    elem._msg = msg;

    GameplayDataActor.CommandKit kit = getKit(actor.getCommandMap(), msg.type());
    elem._kit = kit;

    elem._req = new DataLoadRequestMaker(kit, msg.param(), actor.getLujcache()).make();
    return elem;
  }

  private GameplayDataActor.CommandKit getKit(
      Map<Class<?>, GameplayDataActor.CommandKit> cmdMap, Class<?> cmdType) {
    GameplayDataActor.CommandKit kit = cmdMap.get(cmdType);
    return checkNotNull(kit, cmdType.getName());
  }

  static class Elem {

    CmdGroupExecMsg.Command _msg;

    GameplayDataActor.CommandKit _kit;

    CacheRequest _req;
  }
}
