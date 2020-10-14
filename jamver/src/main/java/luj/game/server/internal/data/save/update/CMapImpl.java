package luj.game.server.internal.data.save.update;

import java.util.Map;
import java.util.Set;
import luj.game.server.api.plugin.JamverDataSaveUpdate;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgMap;

final class CMapImpl implements JamverDataSaveUpdate.MapChanged {

  @Override
  public Map<Object, Object> updated() {
    return _map.getUpdateHistory();
  }

  @Override
  public Set<Object> removed() {
    return _map.getRemoveHistory();
  }

  DUpdateMsgMap _map;
}
