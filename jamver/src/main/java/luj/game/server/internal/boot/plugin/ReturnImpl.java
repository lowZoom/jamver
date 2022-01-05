package luj.game.server.internal.boot.plugin;

import static com.google.common.base.Preconditions.checkState;

import java.util.function.Function;
import luj.game.server.api.plugin.JamverBootRootInit;

final class ReturnImpl implements JamverBootRootInit.Return, BootStartInvoker.Result {

  @Override
  public JamverBootRootInit.Return cluster(
      Function<JamverBootRootInit.Cluster, JamverBootRootInit.Cluster> val) {
    checkState(val.apply(_cluster) == _cluster);
    return this;
  }

  @Override
  public JamverBootRootInit.Return network(
      Function<JamverBootRootInit.Network, JamverBootRootInit.Network> val) {
    checkState(val.apply(_network) == _network);
    return this;
  }

  @Override
  public JamverBootRootInit.Return injectExtra(
      Function<JamverBootRootInit.Inject, JamverBootRootInit.Inject> val) {
    checkState(val.apply(_injectExtra) == _injectExtra);
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
  public BootStartInvoker.Network networkConfig() {
    return _network;
  }

  @Override
  public BootStartInvoker.Inject injectExtra() {
    return _injectExtra;
  }

  @Override
  public Object startParam() {
    return _param;
  }

  /////////////////////////////////////////////

  ClusterImpl _cluster;
  NetworkImpl _network;

  InjectImpl _injectExtra;
  Object _param;
}
