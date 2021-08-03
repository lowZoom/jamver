package luj.game.server.internal.data.id.generate;

import luj.game.server.api.plugin.JamverDataIdGenNext;

final class ContextImpl implements JamverDataIdGenNext.Context {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getGenerateState(JamverDataIdGenNext<T> plugin) {
    return (T) _appState;
  }

  Object _appState;
}
