package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableList;
import java.util.Map;
import luj.ava.spring.Internal;
import luj.cache.api.CacheSession;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.CacheRequest;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.internal.data.command.queue.add.WaitQueueAdder;
import luj.game.server.internal.data.execute.finish.CommandExecFinisher;
import luj.game.server.internal.data.execute.load.DataLoadRequestMaker;
import luj.game.server.internal.data.execute.load.missing.DataReadyChecker;
import luj.game.server.internal.data.execute.load.request.MissingLoadRequestor;
import luj.game.server.internal.data.id.state.DataIdGenState;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

@Internal
final class OnDataCmdExec implements GameplayDataActor.Handler<DatacmdExecMsg> {

  @Override
  public void onHandle(Context ctx) {
    GameplayDataActor self = ctx.getActorState(this);
    DatacmdExecMsg msg = ctx.getMessage(this);

    String cmdType = msg.getCmdType();
//    LOG.debug("尝试执行CMD：{}", cmdType);

    Map<String, GameplayDataActor.CommandKit> cmdMap = self.getCommandMap();
    Object param = msg.getParam();

    GameplayDataActor.CommandKit cmdKit = cmdMap.get(cmdType);
    checkNotNull(cmdKit, cmdType);

    //TODO: 调用外部load创建数据使用req

    //TODO: 遍历得出借不出的数据

    //TODO: 如果有，中断，等有数据归还时再触发检测

    //TODO: 如果没有，进行数据借出锁定，创建结果对象，以供CMD使用

    CacheContainer dataCache = self.getDataCache();
    CacheSession lujcache = self.getLujcache();
    CacheRequest cacheReq = DataLoadRequestMaker.create(cmdKit, param, dataCache, lujcache).make();

    DataReadyChecker.Result readyResult = new DataReadyChecker(
        ImmutableList.of(cacheReq), dataCache).check();

    //TODO: 还有客户端网络连接
    ServerMessageHandler.Server remoteRef = msg.getRemoteRef();

    DataIdGenState idState = self.getIdGenState();
    if (!readyResult.isReady()) {
      // 发起数据读取
      new MissingLoadRequestor(readyResult.getMissingList(),
          idState.getIdField(), dataCache, self.getLoadRef()).request();

      // 加入等待队列
      new WaitQueueAdder(self.getCommandQueue(), cmdKit, param, cacheReq, remoteRef).add();

      return;
    }

    Ref selfRef = ctx.getActorRef();
    new CommandExecFinisher(cmdKit.getLoadResultType(), cacheReq, dataCache, idState,
        cmdType, cmdKit, param, selfRef, self.getSaveRef(), remoteRef, self.getLujbean()).finish();
  }

//  private static final Logger LOG = LoggerFactory.getLogger(OnDataCmdExec.class);
}
