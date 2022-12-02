package luj.game.server.internal.boot.plugin.start;

import luj.game.server.api.plugin.JamverBootRootInit;

final class InternalImpl implements JamverBootRootInit.Internal, BootStartInvoker.Internal {

  @Override
  public JamverBootRootInit.Network network() {
    return _network;
  }

  @Override
  public BootStartInvoker.Network network0() {
    return (BootStartInvoker.Network) _network;
  }

  JamverBootRootInit.Network _network;
}
