package luj.game.server.internal.data.id.init;

import luj.game.server.api.plugin.JamverDataIdInit;

final class InitContextImpl implements JamverDataIdInit.Context {

  @Override
  public <T> T getDataState() {
    throw new UnsupportedOperationException("getDataState尚未实现");
  }

  @Override
  public JamverDataIdInit.Return finishWith() {
    return _return;
  }

  InitReturnImpl _return;
}
