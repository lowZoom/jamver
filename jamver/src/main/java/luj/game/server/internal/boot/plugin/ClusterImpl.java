package luj.game.server.internal.boot.plugin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Set;
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
  public JamverBootRootInit.Cluster selfName(String val) {
    _selfName = val;
    return this;
  }

  @Override
  public JamverBootRootInit.Cluster selfTags(Set<String> val) {
    _selfTags = val;
    return this;
  }

  @Override
  public JamverBootRootInit.Cluster seedList(List<String> val) {
    return discoveryAkkaSeed(val);
  }

  @Override
  public JamverBootRootInit.Cluster discoveryAkkaSeed(List<String> val) {
    _akkaSeed = val;
    return this;
  }

  @Override
  public JamverBootRootInit.Cluster discoveryConsulHost(String val) {
    _consulHost = val;
    return this;
  }

  @Override
  public JamverBootRootInit.Cluster discoveryConsulPort(int val) {
    _consulPort = val;
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
  public String selfName() {
    return _selfName;
  }

  @Override
  public List<String> selfTags() {
    return ImmutableList.copyOf(_selfTags);
  }

  @Override
  public List<String> akkaSeed() {
    return _akkaSeed;
  }

  @Override
  public String consulHost() {
    return _consulHost;
  }

  @Override
  public int consulPort() {
    return _consulPort;
  }

  /////////////////////////////////////////////

  String _selfHost;
  int _selfPort;

  String _selfName;
  Set<String> _selfTags = ImmutableSet.of();

  List<String> _akkaSeed = ImmutableList.of();
  String _consulHost;
  int _consulPort;
}
