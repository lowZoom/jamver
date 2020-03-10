package luj.game.server.internal.data.execute;

import luj.game.server.api.data.DataCommandException;
import luj.game.server.api.data.GameDataCommand;
import org.slf4j.Logger;

final class CommandContextImpl implements GameDataCommand.Context {

  CommandContextImpl(Object param, Object loadResult, GameDataCommand.Service service) {
    _param = param;
    _loadResult = loadResult;
    _service = service;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <P> P param(GameDataCommand<P, ?> cmd) {
    return (P) _param;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <D> D data(GameDataCommand<?, D> cmd) {
    return (D) _loadResult;
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
    return _service;
  }

  private final Object _param;
  private final Object _loadResult;

  private final GameDataCommand.Service _service;
}
