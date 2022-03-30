package luj.game.server.internal.config.reload;

import luj.game.server.api.plugin.JamverConfigReload;

final class ReloadContextImpl implements JamverConfigReload.Context {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getStartParam() {
    return (T) _startParam;
  }

  Object _startParam;
}
