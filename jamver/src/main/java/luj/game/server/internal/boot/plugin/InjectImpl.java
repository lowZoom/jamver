package luj.game.server.internal.boot.plugin;

import com.google.common.collect.ImmutableList;
import java.util.List;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.plugin.JamverBootRootInit;

final class InjectImpl implements JamverBootRootInit.Inject, BootStartInvoker.Inject {

  @Override
  public JamverBootRootInit.Inject startListeners(List<GameStartListener> val) {
    _startListeners = val;
    return this;
  }

  /////////////////////////////////////////////

  @Override
  public List<GameStartListener> startListeners() {
    return _startListeners;
  }

  /////////////////////////////////////////////

  List<GameStartListener> _startListeners = ImmutableList.of();
}
