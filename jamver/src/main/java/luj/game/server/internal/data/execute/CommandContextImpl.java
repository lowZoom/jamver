package luj.game.server.internal.data.execute;

import luj.game.server.api.data.DataCommandException;
import luj.game.server.api.data.GameDataCommand;
import org.slf4j.Logger;

final class CommandContextImpl implements GameDataCommand.Context {

  CommandContextImpl(Object param) {
    _param = param;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <P> P param(GameDataCommand<P, ?> cmd) {
    return (P) _param;
  }

  @Override
  public <D> D data(GameDataCommand<?, D> cmd) {
    return null;
  }

  @Override
  public DataCommandException error(String messageTemplate, Object... messageArgs) {
    return null;
  }

  @Override
  public Logger logger() {
    return null;
  }

  @Override
  public GameDataCommand.Service service() {
    return null;
  }

  private final Object _param;
}
