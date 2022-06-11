package luj.game.server.internal.boot.plugin.start;

import static com.google.common.base.MoreObjects.firstNonNull;

import com.google.common.collect.ImmutableList;
import java.util.List;
import luj.game.server.api.boot.GameStartListener;
import luj.game.server.api.plugin.JamverBootRootInit;

final class InjectImpl implements JamverBootRootInit.Inject, BootStartInvoker.Inject {

  @Override
  public JamverBootRootInit.Inject startListeners(List<GameStartListener> val) {
    _startListeners = firstNonNull(val, ImmutableList.of());
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
