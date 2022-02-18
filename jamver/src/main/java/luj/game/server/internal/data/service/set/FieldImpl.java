package luj.game.server.internal.data.service.set;

import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.data.load.result.DataResultProxy;
import luj.game.server.internal.data.types.map.history.MapDataUpdater;

@Deprecated
public class FieldImpl<T> implements GameDataCommand.Data.Field<T> {

  public FieldImpl(DataResultProxy curData, String curField) {
    _curData = curData;
    _curField = curField;
  }

  @Override
  public void $(T value) {
    MapDataUpdater.GET.update(_curData.getData().getDataMapV2(), _curField, value);
  }

  private final DataResultProxy _curData;

  private final String _curField;
}
