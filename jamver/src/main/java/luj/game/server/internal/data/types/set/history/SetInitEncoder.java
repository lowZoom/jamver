package luj.game.server.internal.data.types.set.history;

import com.google.common.collect.ImmutableSet;
import java.util.Set;

public enum SetInitEncoder {
  GET;

  public Set<?> encode(SetWithHistory<?> set) {
    return ImmutableSet.copyOf(set.getData());
  }
}
