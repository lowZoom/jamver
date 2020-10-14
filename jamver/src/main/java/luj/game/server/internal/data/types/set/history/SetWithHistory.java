package luj.game.server.internal.data.types.set.history;

import java.util.LinkedHashSet;
import java.util.Set;

public class SetWithHistory<E> {

  public SetWithHistory(LinkedHashSet<E> data) {
    _data = data;
  }

  public Set<E> getAddHistory() {
    return _addHistory;
  }

  public void setAddHistory(Set<E> addHistory) {
    _addHistory = addHistory;
  }

  public Set<E> getRemoveHistory() {
    return _removeHistory;
  }

  public void setRemoveHistory(Set<E> removeHistory) {
    _removeHistory = removeHistory;
  }

  public LinkedHashSet<E> getData() {
    return _data;
  }

  @Override
  public String toString() {
    return _data.toString();
  }

  private Set<E> _addHistory;
  private Set<E> _removeHistory;

  private final LinkedHashSet<E> _data;
}
