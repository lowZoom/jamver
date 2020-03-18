package luj.game.server.internal.data.save.init;

import luj.game.server.api.plugin.JamverDataSaveInit;

final class InitContextImpl implements JamverDataSaveInit.Context {

  InitContextImpl(Object dataState) {
    _dataState = dataState;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getDataState() {
    return (T) _dataState;
  }

  private final Object _dataState;
}
