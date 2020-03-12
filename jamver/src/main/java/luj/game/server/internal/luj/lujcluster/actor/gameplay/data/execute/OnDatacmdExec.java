package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.execute;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import luj.ava.spring.Internal;
import luj.cache.api.container.CacheContainer;
import luj.game.server.internal.data.execute.DataCmdExecutor;
import luj.game.server.internal.data.execute.DataServiceImpl;
import luj.game.server.internal.data.execute.load.DataLoadRequestMaker;
import luj.game.server.internal.data.instance.DataTempAdder;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.GameplayDataActor;

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
    Class<?> loadResultType = cmdKit.getLoadResultType();
    CacheContainer dataCache = actor.getDataCache();

    List<DataTempProxy> createLog = new ArrayList<>();
    DataServiceImpl dataSvc = new DataServiceImpl(ctx.getActorRef(), createLog);

    Object loadResult = new DataLoadRequestMaker(cmdKit.getLoader(), loadResultType,
        param, dataCache, actor.getLujcache(), dataSvc::specifySetField).make();

    //TODO: 遍历得出借不出的数据

    //TODO: 如果有，中断，等有数据归还时再触发检测

    //TODO: 如果没有，进行数据借出锁定，创建结果对象，以供CMD使用

    ctx.getLogger().debug("执行数据CMD：{}", cmdType.getName());
    new DataCmdExecutor(cmdKit, param, loadResult, dataSvc).execute();

    for (DataTempProxy data : createLog) {
      new DataTempAdder(dataCache, data.getDataType(), data).add();
    }
  }
}
