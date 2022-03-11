package luj.game.server.internal.luj.lujcluster.actor.cluster.health;

import java.util.List;

public class ClusterUpdateHealthMsg {

  public ClusterUpdateHealthMsg(String id, List<String> tags, boolean healthy) {
    _id = id;
    _tags = tags;
    _healthy = healthy;
  }

  public String getId() {
    return _id;
  }

  public List<String> getTags() {
    return _tags;
  }

  public boolean isHealthy() {
    return _healthy;
  }

  private final String _id;

  private final List<String> _tags;
  private final boolean _healthy;
}
