package luj.game.server.internal.cluster.join;

import java.util.List;
import java.util.Map;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.Tellable;
import luj.cluster.api.node.NodeNewMemberListener;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class ClusterJoinFirer {

  public ClusterJoinFirer(List<ServerJoinListener> listenerList,
      NodeNewMemberListener.NodeRemote remoteNode, Tellable clusterRef,
      Map<String, GameplayDataActor.CommandKit> commandMap, BeanContext lujbean, Tellable dataRef) {
    _listenerList = listenerList;
    _remoteNode = remoteNode;
    _clusterRef = clusterRef;
    _commandMap = commandMap;
    _lujbean = lujbean;
    _dataRef = dataRef;
  }

  public void fire() throws Exception {
    ServerImpl server = new ServerImpl();
    server._remoteNode = _remoteNode;
    server._clusterRef = _clusterRef;

    JoinServiceImpl joinSvc = new JoinServiceImpl();
    joinSvc._dataService = createDataSvc();

    JoinContextImpl ctx = new JoinContextImpl();
    ctx._remoteServer = server;
    ctx._service = joinSvc;

    for (ServerJoinListener listener : _listenerList) {
      listener.onHandle(ctx);
    }
  }

  private DataServiceImpl createDataSvc() {
    DataServiceImpl svc = new DataServiceImpl();
    svc._commandMap = _commandMap;
    svc._lujbean = _lujbean;
    svc._dataRef = _dataRef;
    return svc;
  }

  private final List<ServerJoinListener> _listenerList;

  private final NodeNewMemberListener.NodeRemote _remoteNode;
  private final Tellable _clusterRef;

  private final Map<String, GameplayDataActor.CommandKit> _commandMap;
  private final BeanContext _lujbean;
  private final Tellable _dataRef;
}
