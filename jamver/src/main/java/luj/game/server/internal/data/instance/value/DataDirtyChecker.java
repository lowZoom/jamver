package luj.game.server.internal.data.instance.value;

import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.types.HasOp;
import luj.game.server.internal.data.types.map.history.MapChangedChecker;
import luj.game.server.internal.data.types.map.history.MapWithHistory;

public enum DataDirtyChecker {
  GET;

  public boolean isDirty(DataTempProxy data) {
    MapWithHistory<String, Object> value = data.getDataMapV2();
    if (MapChangedChecker.GET.isChanged(value)) {
      return true;
    }

    return value.getData().values().stream()
        .filter(d -> d instanceof HasOp)
        .map(d -> ((HasOp) d).<ChangedApplicable>getDataOp())
        .anyMatch(ChangedApplicable::isChanged);
  }
}
