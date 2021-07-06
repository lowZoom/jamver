package luj.game.server.internal.data.command.service;

import java.time.Duration;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import luj.game.server.api.data.service.CommandService;
import luj.game.server.internal.data.service.param.CommandParamMaker;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.DatacmdExecMsg;

final class CommandServiceImpl<P> implements CommandService<P> {

  @Override
  public void execute(BiFunction<Param, P, Param> param) {
    execute0(param == null ? null : param::apply);
  }

  /**
   * @see luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.OnDataCmdExec#onHandle
   */
  @SuppressWarnings("unchecked")
  @Override
  public void execute0(BiConsumer<Param, P> param) {
    Object paramObj = (_paramType == Void.class) ? null : new CommandParamMaker(
        _paramType, (BiConsumer<CommandService.Param, Object>) param, _factory._lujbean).make();

    DatacmdExecMsg msg = new DatacmdExecMsg(_commandType.getName(), paramObj, _factory._remoteRef);
    _factory._dataRef.tell(msg);
  }

  @Override
  public void schedule(Duration delay) {
    throw new UnsupportedOperationException("schedule尚未实现");
  }

  @Override
  public void schedule(Duration delay, BiFunction<Param, P, Param> param) {
    throw new UnsupportedOperationException("schedule尚未实现");
  }

  @Override
  public void cancelSchedule() {
    throw new UnsupportedOperationException("cancelSchedule尚未实现");
  }

  Class<?> _commandType;
  Class<?> _paramType;

  CommandServiceFactory _factory;
}
