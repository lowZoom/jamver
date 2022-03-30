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
import luj.game.server.internal.data.id.state.DataIdGenState;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

@Internal
final class OnCmdGroupExec implements GameplayDataActor.Handler<CmdGroupExecMsg> {

  @Override
  public void onHandle(Context ctx) {
    GameplayDataActor self = ctx.getActorState(this);
    CmdGroupExecMsg msg = ctx.getMessage(this);

    List<Elem> cmdList = msg.commands().stream()
        .map(m -> makeElem(m, self))
        .collect(Collectors.toList());

    List<CacheRequest> reqList = cmdList.stream()
        .map(c -> c._req)
        .collect(Collectors.toList());

    Map<Class<?>, GameplayDataActor.GroupKit> groupMap = self.getCmdGroupMap();
    Class<?> groupType = msg.groupType();

    GameplayDataActor.GroupKit groupKit = groupMap.get(groupType);
    checkNotNull(groupKit, groupType.getName());

    List<GroupReqElement> elemList = cmdList.stream()
        .map(c -> new GroupReqElement(c._kit, c._msg.param(), c._req))
        .collect(Collectors.toList());

    DataReadyChecker.Result readyResult = new DataReadyChecker(reqList).check();
    DataIdGenState idState = self.getIdGenState();
    CacheContainer dataCache = self.getDataCache();

    //TODO: 还有网络连接
    ServerMessageHandler.Server remoteRef = msg.remoteRef();

    if (!readyResult.isReady()) {
      // 发起数据读取
      new MissingLoadRequestor(readyResult.getMissingList(),
          idState.getIdField(), dataCache, self.getLoadRef()).request();

      // 加入等待队列
      new WaitQueueGroupAdder(self.getCommandQueue(),
          groupKit.getGroup(), elemList, remoteRef).addGroup();

      return;
    }

    Ref selfRef = ctx.getActorRef();
    new GroupExecFinisher(groupKit.getGroup(), elemList, dataCache, self.getConfigs(), idState,
        selfRef, self.getSaveRef(), remoteRef, self.getCommandMap(), self.getLujbean()).finish();
  }

  private Elem makeElem(CmdGroupExecMsg.Command msg, GameplayDataActor actor) {
    Elem elem = new Elem();
    elem._msg = msg;

    GameplayDataActor.CommandKit kit = getKit(actor.getCommandMap(), msg.type());
    elem._kit = kit;

    elem._req = DataLoadRequestMaker.create(kit,
        msg.param(), actor.getDataCache(), actor.getLujcache()).make();

    return elem;
  }

  private GameplayDataActor.CommandKit getKit(
      Map<String, GameplayDataActor.CommandKit> cmdMap, Class<?> cmdType) {
    String typeName = cmdType.getName();
    GameplayDataActor.CommandKit kit = cmdMap.get(typeName);
    return checkNotNull(kit, typeName);
  }

  static class Elem {

    CmdGroupExecMsg.Command _msg;

    GameplayDataActor.CommandKit _kit;

    CacheRequest _req;
  }
}
