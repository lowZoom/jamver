package luj.game.server.internal.data.command.queue;

import java.util.List;
import luj.cache.api.request.CacheRequest;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.internal.data.command.queue.element.GroupReqElement;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class DataCommandRequest {

  public GameplayDataActor.CommandKit getCommandKit() {
    return _commandKit;
  }

  public Object getCommandParam() {
    return _commandParam;
  }

  public CacheRequest getCacheReq() {
    return _cacheReq;
  }

  public GameDataCommandGroup getCmdGroup() {
    return _cmdGroup;
  }

  public List<GroupReqElement> getGroupElemList() {
    return _groupElemList;
  }

  public ServerMessageHandler.Server getRemoteRef() {
    return _remoteRef;
  }

  GameplayDataActor.CommandKit _commandKit;
  Object _commandParam;
  CacheRequest _cacheReq;

  GameDataCommandGroup _cmdGroup;
  List<GroupReqElement> _groupElemList;

  ServerMessageHandler.Server _remoteRef;
}
