package luj.game.server.internal.data.command.queue;

import java.util.List;
import luj.cache.api.request.CacheRequest;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.internal.data.command.queue.element.GroupReqElement;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class CommandRequestFactory {

  public CommandRequestFactory(ServerMessageHandler.Server remoteRef) {
    _remoteRef = remoteRef;
  }

  public DataCommandRequest createCommand(GameplayDataActor.CommandKit cmdKit,
      Object cmdParam, CacheRequest cacheReq) {
    DataCommandRequest req = makeReq();
    req._commandKit = cmdKit;
    req._commandParam = cmdParam;
    req._cacheReq = cacheReq;
    return req;
  }

  public DataCommandRequest createGroup(GameDataCommandGroup group,
      List<GroupReqElement> elemList) {
    DataCommandRequest req = makeReq();
    req._cmdGroup = group;
    req._groupElemList = elemList;
    return req;
  }

  private DataCommandRequest makeReq() {
    DataCommandRequest req = new DataCommandRequest();
    req._remoteRef = _remoteRef;
    return req;
  }

  private final ServerMessageHandler.Server _remoteRef;
}
