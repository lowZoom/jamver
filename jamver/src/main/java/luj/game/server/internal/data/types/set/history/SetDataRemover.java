package luj.game.server.internal.data.types.set.history;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum SetDataRemover {
  GET;

  @SuppressWarnings("unchecked")
  public <E> boolean remove(SetWithHistory<E> set, Object elem) {
    //TODO: 清理添加历史

    if (!set.getData().contains(elem)) {
      return false;
    }
    return getOrNewRemoveHistory(set).add((E) elem);
  }

  @SuppressWarnings("unchecked")
  public <E> boolean removeAll(SetWithHistory<E> set, Collection<?> elem) {
    //TODO: 清理添加历史

    Set<E> data = set.getData();
    Set<E> removeSet = elem.stream()
        .filter(data::contains)
        .map(e -> (E) e)
        .collect(Collectors.toSet());

    if (removeSet.isEmpty()) {
      return false;
    }
    return getOrNewRemoveHistory(set).addAll(removeSet);
  }

  private <E> Set<E> getOrNewRemoveHistory(SetWithHistory<E> set) {
    Set<E> old = set.getRemoveHistory();
    if (old != null) {
      return old;
    }
    Set<E> newHistory = new LinkedHashSet<>();
    set.setRemoveHistory(newHistory);
    return newHistory;
  }
}
