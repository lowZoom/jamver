package luj.game.server.internal.data.service.set;

import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.data.load.result.DataResultProxy;

public class FieldImpl<T> implements GameDataCommand.Data.Field<T> {

  public FieldImpl(DataResultProxy curData, String curField) {
    _curData = curData;
    _curField = curField;
  }

  @Override
  public void $(T value) {
    _curData.setDirty(true);
    _curData.getData().getDataMap().put(_curField, value);
  }

  private final DataResultProxy _curData;

  private final String _curField;
}
