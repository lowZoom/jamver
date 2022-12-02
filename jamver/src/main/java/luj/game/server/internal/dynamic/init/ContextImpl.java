package luj.game.server.internal.dynamic.init;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import luj.game.server.api.plugin.JamverDynamicRootInit;

final class ContextImpl implements JamverDynamicRootInit.Context {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getStartParam() {
    return (T) _startParam;
  }

  @Override
  public void registerAll(Collection<?> beans) {
    _registerAll = beans;
  }

  Object _startParam;

  Collection<?> _registerAll = ImmutableList.of();
}
