package luj.game.server.internal.data.execute.service.data;

import java.time.Duration;
import java.util.function.BiConsumer;
import luj.game.server.api.data.service.CommandService;
import luj.game.server.internal.data.service.param.CommandParamMaker;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.DatacmdExecMsg;

final class CommandServiceImpl<P> implements CommandService<P> {

  @SuppressWarnings("unchecked")
  @Override
  public void execute(BiConsumer<Param, P> param) {
    Object paramObj = (_paramType == Void.class) ? null : new CommandParamMaker(_paramType,
        (BiConsumer<CommandService.Param, Object>) param, _dataSvc.getLujbean()).make();

    DatacmdExecMsg msg = new DatacmdExecMsg(_commandType, paramObj, _dataSvc.getRemoteRef());
    _dataSvc.getDataRef().tell(msg);
  }

  @Override
  public void schedule(Duration delay) {
    throw new UnsupportedOperationException("schedule尚未实现");
  }

  @Override
  public void schedule(Duration delay, BiConsumer<Param, P> param) {
    throw new UnsupportedOperationException("schedule尚未实现");
  }

  @Override
  public void cancelSchedule() {
    throw new UnsupportedOperationException("cancelSchedule尚未实现");
  }

  Class<?> _commandType;
  Class<?> _paramType;

  DataServiceImpl _dataSvc;
}
