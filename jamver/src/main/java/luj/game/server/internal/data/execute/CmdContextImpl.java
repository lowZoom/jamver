package luj.game.server.internal.data.execute;

import luj.game.server.api.data.GameDataCommand;
import org.slf4j.Logger;

final class CmdContextImpl implements GameDataCommand.Context {

  CmdContextImpl(Object param, Object loadResult, Logger logger, GameDataCommand.Service service) {
    _param = param;
    _loadResult = loadResult;
    _logger = logger;
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
  public Logger logger() {
    //TODO: 是不是要直接用slf4j的接口还要再想想
    return _logger;
  }

  @Override
  public GameDataCommand.Service service() {
    return _service;
  }

  private final Object _param;
  private final Object _loadResult;

  private final Logger _logger;
  private final GameDataCommand.Service _service;
}
