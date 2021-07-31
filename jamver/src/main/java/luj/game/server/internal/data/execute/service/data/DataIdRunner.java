package luj.game.server.internal.data.execute.service.data;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Proxy;
import luj.game.server.internal.data.load.result.DataResultProxyV2;

enum DataIdRunner {
  GET;

  Comparable<?> run(Object data) {
    DataResultProxyV2 proxy = (DataResultProxyV2) Proxy.getInvocationHandler(data);
//    T id = proxy.getData().getDataMap().get(DataTempProxy.ID);
    Comparable<?> id = proxy.getData().getDataId();
    return checkNotNull(id);
  }
}
