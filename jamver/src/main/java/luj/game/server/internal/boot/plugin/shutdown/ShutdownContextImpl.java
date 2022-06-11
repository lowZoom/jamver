package luj.game.server.internal.boot.plugin.shutdown;

import luj.game.server.api.plugin.JamverBootShutdown;

final class ShutdownContextImpl implements JamverBootShutdown.Context {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getShutdownParam() {
    return (T) _shutdownParam;
  }

  Object _shutdownParam;
}
