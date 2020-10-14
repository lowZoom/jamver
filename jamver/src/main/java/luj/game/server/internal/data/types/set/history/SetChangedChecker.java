package luj.game.server.internal.data.types.set.history;

import luj.ava.collection.CollectionX;

public enum SetChangedChecker {
  GET;

  public boolean isChanged(SetWithHistory<?> set) {
    return !CollectionX.nonNull(set.getAddHistory()).isEmpty()
        || !CollectionX.nonNull(set.getRemoveHistory()).isEmpty();
  }
}
