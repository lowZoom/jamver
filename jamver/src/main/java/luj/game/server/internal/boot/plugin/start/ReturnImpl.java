package luj.game.server.internal.boot.plugin.start;

import static com.google.common.base.Preconditions.checkState;

import java.util.function.Function;
import luj.game.server.api.plugin.JamverBootRootInit;

final class ReturnImpl implements JamverBootRootInit.Return, BootStartInvoker.Config {

  @Override
  public JamverBootRootInit.Return cluster(
      Function<JamverBootRootInit.Cluster, JamverBootRootInit.Cluster> val) {
    checkState(val.apply(_cluster) == _cluster);
    return this;
  }

  @Override
  public JamverBootRootInit.Return param(
      Function<JamverBootRootInit.Param, JamverBootRootInit.Param> val) {
    checkState(val.apply(_param) == _param);
    return this;
  }

  /////////////////////////////////////////////

  @Override
  public BootStartInvoker.Cluster cluster() {
    return _cluster;
  }

  @Override
  public BootStartInvoker.Param param() {
    return _param;
  }

  /////////////////////////////////////////////

  RClusterImpl _cluster;

  RParamImpl _param;
}
