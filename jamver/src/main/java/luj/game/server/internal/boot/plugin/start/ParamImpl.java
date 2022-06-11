package luj.game.server.internal.boot.plugin.start;

import luj.game.server.api.plugin.JamverBootRootInit;

final class ParamImpl implements JamverBootRootInit.Param, BootStartInvoker.Param {

  @Override
  public JamverBootRootInit.Param start(Object val) {
    _start = val;
    return this;
  }

  @Override
  public JamverBootRootInit.Param shutdown(Object val) {
    _shutdown = val;
    return this;
  }

  /////////////////////////////////////////////

  @Override
  public Object start() {
    return _start;
  }

  @Override
  public Object shutdown() {
    return _shutdown;
  }

  Object _start;

  Object _shutdown;
}
