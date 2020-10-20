package luj.game.server.internal.data.types.map.history;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import luj.ava.collection.CollectionX;
import luj.ava.collection.map.MapX;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgMap;

public enum MapChangeEncoder {
  GET;

  @SuppressWarnings("unchecked")
  public DUpdateMsgMap encode(MapWithHistory<?, ?> map) {
    MapWithHistory<Object, Object> mapVar = (MapWithHistory<Object, Object>) map;

    return new DUpdateMsgMap(
        ImmutableMap.copyOf(MapX.nonNull(mapVar.getUpdateHistory())),
        ImmutableSet.copyOf(CollectionX.nonNull(mapVar.getRemoveHistory())));
  }
}
