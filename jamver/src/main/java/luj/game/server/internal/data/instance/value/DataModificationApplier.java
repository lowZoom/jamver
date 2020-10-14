package luj.game.server.internal.data.instance.value;

import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.types.HasOp;
import luj.game.server.internal.data.types.map.history.MapModificationApplier;
import luj.game.server.internal.data.types.map.history.MapWithHistory;

public class DataModificationApplier {

  public DataModificationApplier(DataTempProxy data) {
    _data = data;
  }

  public void apply() {
    applyPrimitive();
    applyObject();
  }

  private void applyPrimitive() {
    MapWithHistory<String, Object> dataMap = _data.getDataMapV2();
    MapModificationApplier.GET.apply(dataMap);
  }

  private void applyObject() {
    _data.getDataMapV2().getData().values().stream()
        .filter(d -> d instanceof HasOp)
        .map(d -> ((HasOp) d).<ChangedApplicable>getDataOp())
        .filter(ChangedApplicable::isChanged)
        .forEach(ChangedApplicable::applyChanged);
  }

  private final DataTempProxy _data;
}
