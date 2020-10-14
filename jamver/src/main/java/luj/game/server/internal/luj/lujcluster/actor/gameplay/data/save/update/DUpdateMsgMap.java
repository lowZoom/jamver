package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update;

import java.util.Map;
import java.util.Set;

public class DUpdateMsgMap {

  public DUpdateMsgMap(Map<Object, Object> updateHistory, Set<Object> removeHistory) {
    _updateHistory = updateHistory;
    _removeHistory = removeHistory;
  }

  public Map<Object, Object> getUpdateHistory() {
    return _updateHistory;
  }

  public Set<Object> getRemoveHistory() {
    return _removeHistory;
  }

  private final Map<Object, Object> _updateHistory;

  private final Set<Object> _removeHistory;
}
