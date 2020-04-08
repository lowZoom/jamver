package luj.game.server.internal.luj.lujcluster.actor.cluster;

import luj.game.server.api.plugin.JamverClusterProtoDecode;
import luj.game.server.api.plugin.JamverClusterProtoEncode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @see luj.game.server.internal.inject.ServerBeanCollector#collect
 */
public class ClusterProtoPlugin {

  public JamverClusterProtoEncode getProtoEncode() {
    return _protoEncode;
  }

  public JamverClusterProtoDecode getProtoDecode() {
    return _protoDecode;
  }

  @Autowired
  private JamverClusterProtoEncode _protoEncode;

  @Autowired
  private JamverClusterProtoDecode _protoDecode;
}
