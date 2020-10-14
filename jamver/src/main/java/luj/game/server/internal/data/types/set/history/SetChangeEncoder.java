package luj.game.server.internal.data.types.set.history;

import com.google.common.collect.ImmutableSet;
import luj.ava.collection.CollectionX;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgSet;

public enum SetChangeEncoder {
  GET;

  @SuppressWarnings("unchecked")
  public DUpdateMsgSet encode(SetWithHistory<?> set) {
    SetWithHistory<Object> setVar = (SetWithHistory<Object>) set;
    return new DUpdateMsgSet(CollectionX.nonNull(setVar.getAddHistory()), ImmutableSet.of());
  }
}
