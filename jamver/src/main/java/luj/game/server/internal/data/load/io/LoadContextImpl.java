package luj.game.server.internal.data.load.io;

import luj.game.server.api.plugin.JamverDataLoadLoad;

final class LoadContextImpl implements JamverDataLoadLoad.Context {

  LoadContextImpl(Object loadState, JamverDataLoadLoad.Data dataReq) {
    _loadState = loadState;
    _dataReq = dataReq;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getLoadState() {
    return (T) _loadState;
  }

  @Override
  public JamverDataLoadLoad.Data getDataRequest() {
    return _dataReq;
  }

  private final Object _loadState;

  private final JamverDataLoadLoad.Data _dataReq;
}
