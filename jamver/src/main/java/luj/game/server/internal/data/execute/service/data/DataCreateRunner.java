package luj.game.server.internal.data.execute.service.data;

import java.util.List;
import luj.game.server.internal.data.id.generate.IdGenerateNextInvoker;
import luj.game.server.internal.data.id.state.DataIdGenState;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.instancev2.DataEntityCreator;
import luj.game.server.internal.data.instancev2.DataType;
import luj.game.server.internal.data.load.result.DataResultProxyV2;

enum DataCreateRunner {
  GET;

  Object run(Class<?> dataClass, DataIdGenState idGenState, List<DataEntity> createLog,
      DataResultProxyV2.FieldHook fieldHook) {
    DataType dataType = DataType.create(dataClass);

    Comparable<?> id = IdGenerateNextInvoker.GET.invoke(idGenState);
    DataEntity dataObj = DataEntityCreator.create(dataType, id).create();

    createLog.add(dataObj);
    DataResultProxyV2 result = DataResultProxyV2.create(dataObj, dataClass, fieldHook);

    return result.getInstance();
  }
}
