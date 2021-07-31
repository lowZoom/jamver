package luj.game.server.internal.data.instance.value.change;

import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.types.HasOp;
import luj.game.server.internal.data.types.map.history.MapModificationApplier;
import luj.game.server.internal.data.types.map.history.MapWithHistory;

public class DataModificationApplier {

  /**
   * @see #createV2
   */
  @Deprecated
  public static DataModificationApplier create(DataTempProxy data) {
    return new DataModificationApplier(data.getDataMapV2());
  }

  public static DataModificationApplier createV2(DataEntity data) {
    return new DataModificationApplier(data.getFieldValueMap());
  }

  public DataModificationApplier(MapWithHistory<String, Object> dataMap) {
    _dataMap = dataMap;
  }

  public void apply() {
    applyPrimitive();
    applyObject();
  }

  private void applyPrimitive() {
    MapModificationApplier.GET.apply(_dataMap);
  }

  private void applyObject() {
    _dataMap.getData().values().stream()
        .filter(d -> d instanceof HasOp)
        .map(d -> ((HasOp) d).<ChangedApplicable>getDataOp())
        .filter(ChangedApplicable::isChanged)
        .forEach(ChangedApplicable::applyChanged);
  }

  private final MapWithHistory<String, Object> _dataMap;
}
