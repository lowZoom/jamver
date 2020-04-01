package luj.game.server.internal.boot.plugin;

import java.util.List;
import luj.game.server.api.plugin.JamverBootRootInit;

final class ClusterImpl implements JamverBootRootInit.Cluster, BootStartInvoker.Cluster {

  @Override
  public JamverBootRootInit.Cluster selfHost(String val) {
    _selfHost = val;
    return this;
  }

  @Override
  public JamverBootRootInit.Cluster selfPort(int val) {
    _selfPort = val;
    return this;
  }

  @Override
  public JamverBootRootInit.Cluster seedList(List<String> val) {
    _seedList = val;
    return this;
  }

  /////////////////////////////////////////////

  @Override
  public String selfHost() {
    return _selfHost;
  }

  @Override
  public int selfPort() {
    return _selfPort;
  }

  @Override
  public List<String> seedList() {
    return _seedList;
  }

  /////////////////////////////////////////////

  String _selfHost;
  int _selfPort;

  List<String> _seedList;
}
