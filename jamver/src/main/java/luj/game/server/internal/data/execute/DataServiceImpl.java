package luj.game.server.internal.data.execute;

import java.time.Duration;
import java.util.function.Supplier;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.data.instance.DataInstanceCreator;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.execute.DatacmdExecMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class DataServiceImpl implements GameDataCommand.Data {

  DataServiceImpl(ActorMessageHandler.Ref dataRef) {
    _dataRef = dataRef;
  }

  @Override
  public <T> T create(Class<T> dataType) {
    return new DataInstanceCreator(dataType).create();
  }

  @Override
  public <T> void set(Supplier<T> field, T value) {
    LOG.warn("尚未实现：set");
  }

  @Override
  public <P> void executeCommand(Class<? extends GameDataCommand<P, ?>> commandType) {
    executeCommand(commandType, null);
  }

  @Override
  public <P> void executeCommand(Class<? extends GameDataCommand<P, ?>> commandType, P param) {
    DatacmdExecMsg msg = new DatacmdExecMsg(commandType, param);
    _dataRef.tell(msg, Duration.ZERO);
  }

  private static final Logger LOG = LoggerFactory.getLogger(DataServiceImpl.class);

  private final ActorMessageHandler.Ref _dataRef;
}
