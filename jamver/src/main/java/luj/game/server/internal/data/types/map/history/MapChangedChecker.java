package luj.game.server.internal.data.types.map.history;

import luj.ava.collection.CollectionX;
import luj.ava.collection.map.MapX;

public enum MapChangedChecker {
  GET;

  public boolean isChanged(MapWithHistory<?, ?> map) {
    return !MapX.nonNull(map.getUpdateHistory()).isEmpty()
        || !CollectionX.nonNull(map.getRemoveHistory()).isEmpty();
  }
}
