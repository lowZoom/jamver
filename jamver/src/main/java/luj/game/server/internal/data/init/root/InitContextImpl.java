package luj.game.server.internal.data.init.root;

import luj.game.server.api.plugin.JamverDataRootInit;

final class InitContextImpl implements JamverDataRootInit.Context {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getStartParam() {
    return (T) _startParam;
  }

  Object _startParam;
}
