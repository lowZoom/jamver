package luj.game.server.internal.data.execute;

import java.util.function.Supplier;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.data.instance.DataInstanceCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class DataServiceImpl implements GameDataCommand.Data {

  @Override
  public <T> T create(Class<T> dataType) {
    return new DataInstanceCreator(dataType).create();
  }

  @Override
  public <T> void set(Supplier<T> field, T value) {
    LOG.warn("尚未实现：set");
  }

  @Override
  public void executeCommand(Class<? extends GameDataCommand<?, ?>> commandType) {
    LOG.warn("尚未实现：executeCommand");
  }

  @Override
  public <P> void executeCommand(Class<? extends GameDataCommand<P, ?>> commandType, P param) {
    LOG.warn("尚未实现：executeCommand");
  }

  private static final Logger LOG = LoggerFactory.getLogger(DataServiceImpl.class);
}
