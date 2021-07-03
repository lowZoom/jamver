package luj.game.server.internal.data.instance.value.change;

import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.types.HasOp;
import luj.game.server.internal.data.types.map.history.MapChangedChecker;
import luj.game.server.internal.data.types.map.history.MapWithHistory;

public enum DataDirtyChecker {
  GET;

  public boolean isDirty(DataTempProxy data) {
    return isDirty0(data.getDataMapV2());
  }

  public boolean isDirty(DataEntity data) {
    return isDirty0(data.getFieldValueMap());
  }

  boolean isDirty0(MapWithHistory<String, Object> value) {
    if (MapChangedChecker.GET.isChanged(value)) {
      return true;
    }

    return value.getData().values().stream()
        .filter(d -> d instanceof HasOp)
        .map(d -> ((HasOp) d).<ChangedApplicable>getDataOp())
        .anyMatch(ChangedApplicable::isChanged);
  }
}
