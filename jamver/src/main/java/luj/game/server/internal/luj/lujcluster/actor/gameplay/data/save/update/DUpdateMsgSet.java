package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update;

import java.util.Set;

public class DUpdateMsgSet {

  public DUpdateMsgSet(Set<Object> addHistory, Set<Object> removeHistory) {
    _addHistory = addHistory;
    _removeHistory = removeHistory;
  }

  public Set<Object> getAddHistory() {
    return _addHistory;
  }

  public Set<Object> getRemoveHistory() {
    return _removeHistory;
  }

  private final Set<Object> _addHistory;

  private final Set<Object> _removeHistory;
}
