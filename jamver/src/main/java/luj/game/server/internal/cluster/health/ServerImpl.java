package luj.game.server.internal.cluster.health;

import java.util.Set;
import luj.game.server.api.cluster.ServerHealthListener;

final class ServerImpl implements ServerHealthListener.Server {

  @Override
  public String id() {
    return _id;
  }

  @Override
  public Set<String> tags() {
    return _tags;
  }

  @Override
  public boolean isHealthy() {
    return _healthy;
  }

  String _id;

  Set<String> _tags;
  boolean _healthy;
}
