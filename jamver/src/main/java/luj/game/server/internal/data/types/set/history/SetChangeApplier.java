package luj.game.server.internal.data.types.set.history;

import java.util.Set;
import luj.ava.collection.CollectionX;

public enum SetChangeApplier {
  GET;

  @SuppressWarnings("unchecked")
  public void apply(SetWithHistory<?> set) {
    SetWithHistory<Object> setVar = (SetWithHistory<Object>) set;
    Set<Object> value = setVar.getData();

    Set<?> addHistory = CollectionX.nonNull(set.getAddHistory());
    value.addAll(addHistory);
  }
}
