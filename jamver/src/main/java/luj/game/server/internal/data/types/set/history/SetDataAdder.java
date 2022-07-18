package luj.game.server.internal.data.types.set.history;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public enum SetDataAdder {
  GET;

  public <E> boolean add(SetWithHistory<E> set, E elem) {
    checkNotNull(elem, "elem");
    boolean isNew = getOrNewAddHistory(set).add(elem);
    //TODO: 清理移除历史
    return isNew;
  }

  public <E> boolean addAll(SetWithHistory<E> set, Collection<? extends E> elem) {
    boolean isNew = getOrNewAddHistory(set).addAll(elem);
    //TODO: 清理移除历史
    return isNew;
  }

  private <E> Set<E> getOrNewAddHistory(SetWithHistory<E> set) {
    Set<E> old = set.getAddHistory();
    if (old != null) {
      return old;
    }
    Set<E> newHistory = new LinkedHashSet<>();
    set.setAddHistory(newHistory);
    return newHistory;
  }
}
