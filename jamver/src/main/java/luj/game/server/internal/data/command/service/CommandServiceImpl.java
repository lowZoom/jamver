package luj.game.server.internal.data.command.service;

import java.time.Duration;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import luj.game.server.api.data.service.CommandService;
import luj.game.server.internal.data.service.param.CommandParamMaker;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.prepare.DatacmdExecMsg;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.schedule.DatacmdScheduleMsg;

final class CommandServiceImpl<P> implements CommandService<P> {

  /**
   * @see luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.prepare.OnDataCmdExec#onHandle
   */
  @Override
  public void execute(BiFunction<Param, P, Param> param) {
    Object paramObj = makeParamObj(param::apply);
    DatacmdExecMsg msg = new DatacmdExecMsg(_commandType.getName(), paramObj, _factory._remoteRef);
    _factory._dataRef.tell(msg);
  }

  @Override
  public void schedule(Duration delay, BiFunction<Param, P, Param> param) {
    String cmdType = _commandType.getName();
    Object paramObj = makeParamObj(param::apply);

    DatacmdScheduleMsg msg = new DatacmdScheduleMsg(cmdType, paramObj, -1, delay);
    _factory._dataRef.tell(msg);
  }

  @Override
  public void schedule(Comparable<?> id, BiFunction<Param, P, Param> param, Duration delay) {
    throw new UnsupportedOperationException("schedule尚未实现");
//    QuartzScheduleStarter.GET.start(_quartz, _commandType.getName(), param, id, delay);
  }

  @Override
  public void cancelSchedule() {
    throw new UnsupportedOperationException("cancelSchedule尚未实现");
  }

  @SuppressWarnings("unchecked")
  private Object makeParamObj(BiConsumer<Param, P> param) {
    //TODO: 校验param里不能包含数据对象

    return (_paramType == Void.class) ? null : new CommandParamMaker(
        _paramType, (BiConsumer<Param, Object>) param, _factory._lujbean).make();
  }

  Class<?> _commandType;
  Class<?> _paramType;

  CommandServiceFactory _factory;
//  Scheduler _quartz;
}
