package luj.game.server.internal.data.execute;

import java.util.function.Supplier;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.data.instance.DataInstanceCreator;

final class DataServiceImpl implements GameDataCommand.Data {

  @Override
  public <T> T create(Class<T> dataType) {
    return new DataInstanceCreator(dataType).create();
  }

  @Override
  public <T> void set(Supplier<T> field, T value) {

  }

  @Override
  public void executeCommand(Class<? extends GameDataCommand<?, ?>> commandType) {

  }

  @Override
  public <P> void executeCommand(Class<? extends GameDataCommand<P, ?>> commandType, P param) {

  }
}
