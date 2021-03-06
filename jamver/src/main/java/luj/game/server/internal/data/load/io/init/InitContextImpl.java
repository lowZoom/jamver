package luj.game.server.internal.data.load.io.init;

import luj.game.server.api.plugin.JamverDataLoadInit;

final class InitContextImpl implements JamverDataLoadInit.Context {

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
