package luj.game.server.internal.data.types.set;

import java.util.LinkedHashSet;
import java.util.Set;
import luj.game.server.internal.data.types.set.history.SetWithHistory;

public enum DataSetFactory {
  GET;

  public <E> Set<E> create() {
    return create(new LinkedHashSet<>());
  }

  public <E> Set<E> create(LinkedHashSet<E> data) {
    SetWithHistory<E> value = new SetWithHistory<>(data);
    SetOp op = new SetOp();

    DataSet<E> set = new DataSet<>(value, op);
    op._set = set;

    return set;
  }
}
