package luj.game.server.internal.data.service.set;

import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.data.load.result.DataResultProxyV2;
import luj.game.server.internal.data.types.map.history.MapDataUpdater;

public class FieldImpl2<T> implements GameDataCommand.Data.Field<T> {

  public FieldImpl2(DataResultProxyV2 curData, String curField) {
    _curData = curData;
    _curField = curField;
  }

  @Override
  public void $(T value) {
    //TODO: 校验不能把null设进原生类型

    //TODO: 校验不能替换掉集合

    MapDataUpdater.GET.update(_curData.getData().getFieldValueMap(), _curField, value);
  }

  private final DataResultProxyV2 _curData;

  private final String _curField;
}
