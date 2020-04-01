package luj.game.server.internal.boot.plugin;

import static com.google.common.base.Preconditions.checkState;

import java.util.function.Function;
import luj.game.server.api.plugin.JamverBootRootInit;

final class ReturnImpl implements JamverBootRootInit.Return, BootStartInvoker.Result {

  ReturnImpl(ClusterImpl cluster) {
    _cluster = cluster;
  }

  @Override
  public JamverBootRootInit.Return cluster(
      Function<JamverBootRootInit.Cluster, JamverBootRootInit.Cluster> val) {
    checkState(val.apply(_cluster) == _cluster);
    return this;
  }

  /**
   * @see #startParam
   */
  @Override
  public JamverBootRootInit.Return param(Object val) {
    _param = val;
    return this;
  }

  /////////////////////////////////////////////

  @Override
  public BootStartInvoker.Cluster clusterConfig() {
    return _cluster;
  }

  @Override
  public Object startParam() {
    return _param;
  }

  /////////////////////////////////////////////

  ClusterImpl _cluster;

  Object _param;
}
