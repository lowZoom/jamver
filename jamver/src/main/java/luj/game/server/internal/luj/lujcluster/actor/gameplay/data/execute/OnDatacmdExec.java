package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.execute;

import java.util.Map;
import luj.ava.spring.Internal;
import luj.game.server.internal.data.execute.DataCmdExecutor;
import luj.game.server.internal.data.execute.load.DataLoadRequestMaker;
import luj.game.server.internal.data.execute.lock.DataLockAndCollector;
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

    //TODO: 调用外部load创建数据使用req
    Class<?> loadResultType = cmdKit.getLoadResultType();
    new DataLoadRequestMaker(cmdKit.getLoader(), loadResultType, param, actor.getLujcache()).make();

    //TODO: 遍历得出借不出的数据

    //TODO: 如果有，中断，等有数据归还时再触发检测

    //TODO: 如果没有，进行数据借出锁定，创建结果对象，以供CMD使用
    Object loadResult = new DataLockAndCollector(loadResultType).lockAndCollect();

    new DataCmdExecutor(cmdKit, param, loadResult).execute();
  }
}
