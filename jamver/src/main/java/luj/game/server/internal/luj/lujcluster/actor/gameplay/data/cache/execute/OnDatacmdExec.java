package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;
import luj.ava.spring.Internal;
import luj.cache.api.CacheSession;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.CacheRequest;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.internal.data.command.queue.add.WaitQueueAdder;
import luj.game.server.internal.data.execute.finish.CommandExecFinisher;
import luj.game.server.internal.data.execute.load.DataLoadRequestMaker;
import luj.game.server.internal.data.execute.load.missing.LoadMissingCollector;
import luj.game.server.internal.data.execute.load.request.MissingLoadRequestor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

@Internal
final class OnDatacmdExec implements GameplayDataActor.Handler<DatacmdExecMsg> {

  @Override
  public void onHandle(Context ctx) {
    GameplayDataActor actor = ctx.getActorState(this);
    DatacmdExecMsg msg = ctx.getMessage(this);

    Map<Class<?>, GameplayDataActor.CommandKit> cmdMap = actor.getCommandMap();
    Class<?> cmdType = msg.getCmdType();
    Object param = msg.getParam();

    GameplayDataActor.CommandKit cmdKit = cmdMap.get(cmdType);
    checkNotNull(cmdKit, cmdType);

    //TODO: 调用外部load创建数据使用req

    //TODO: 遍历得出借不出的数据

    //TODO: 如果有，中断，等有数据归还时再触发检测

    //TODO: 如果没有，进行数据借出锁定，创建结果对象，以供CMD使用

    Class<?> loadResultType = cmdKit.getLoadResultType();
    CacheSession lujcache = actor.getLujcache();

    CacheRequest cacheReq = new DataLoadRequestMaker(cmdKit.getLoader(), loadResultType,
        param, lujcache).make();

    CacheContainer dataCache = actor.getDataCache();
    List<LoadMissingCollector.Missing> missList =
        new LoadMissingCollector(cacheReq, dataCache).collect();

    ActorPreStartHandler.Actor loadRef = actor.getLoadRef();
    if (!missList.isEmpty()) {
      // 发起数据读取
      new MissingLoadRequestor(missList, loadRef).request();

      // 加入等待队列
      new WaitQueueAdder(actor.getCommandQueue(), cmdKit, param, cacheReq).add();

      return;
    }

    new CommandExecFinisher(loadResultType, cacheReq, dataCache, cmdType, cmdKit,
        param, ctx.getActorRef(), actor.getSaveRef()).finish();
  }

//  private static final Logger LOG = LoggerFactory.getLogger(OnDatacmdExec.class);
}
