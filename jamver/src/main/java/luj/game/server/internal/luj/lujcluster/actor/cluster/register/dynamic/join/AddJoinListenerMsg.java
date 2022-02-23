package luj.game.server.internal.luj.lujcluster.actor.cluster.register.dynamic.join;

import java.util.Collection;
import luj.game.server.api.cluster.ServerJoinListener;

public class AddJoinListenerMsg {

  public AddJoinListenerMsg(Collection<ServerJoinListener> joinListener) {
    _joinListener = joinListener;
  }

  public Collection<ServerJoinListener> getJoinListener() {
    return _joinListener;
  }

  private final Collection<ServerJoinListener> _joinListener;
}
