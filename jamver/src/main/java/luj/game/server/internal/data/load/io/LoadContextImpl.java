package luj.game.server.internal.data.load.io;

import luj.game.server.api.plugin.JamverDataLoadIo;

final class LoadContextImpl implements JamverDataLoadIo.Context {

  LoadContextImpl(Object loadState, JamverDataLoadIo.Data dataReq) {
    _loadState = loadState;
    _dataReq = dataReq;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getLoadState() {
    return (T) _loadState;
  }

  @Override
  public JamverDataLoadIo.Data getDataRequest() {
    return _dataReq;
  }

  private final Object _loadState;

  private final JamverDataLoadIo.Data _dataReq;
}
