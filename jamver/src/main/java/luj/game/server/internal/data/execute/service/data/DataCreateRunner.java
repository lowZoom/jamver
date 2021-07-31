package luj.game.server.internal.data.execute.service.data;

import java.util.List;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.instancev2.DataEntityCreator;
import luj.game.server.internal.data.instancev2.DataType;
import luj.game.server.internal.data.load.result.DataResultProxyV2;

enum DataCreateRunner {
  GET;

  Object run(Class<?> dataClass, List<DataEntity> createLog,
      DataResultProxyV2.FieldHook fieldHook) {
    DataType dataType = DataType.create(dataClass);

    //TODO: 生成ID
    DataEntity dataObj = DataEntityCreator.create(dataType, 1L).create();

    createLog.add(dataObj);
    DataResultProxyV2 result = DataResultProxyV2.create(dataObj, dataClass, fieldHook);

    return result.getInstance();
  }
}
