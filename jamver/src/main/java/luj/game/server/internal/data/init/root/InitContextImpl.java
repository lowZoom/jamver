package luj.game.server.internal.data.init.root;

import luj.game.server.api.plugin.JamverDataRootInit;

final class InitContextImpl implements JamverDataRootInit.Context {

  InitContextImpl(Object startParam) {
    _startParam = startParam;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getStartParam() {
    return (T) _startParam;
  }

  private final Object _startParam;
}
