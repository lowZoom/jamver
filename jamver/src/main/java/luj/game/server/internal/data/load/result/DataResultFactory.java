package luj.game.server.internal.data.load.result;

import java.lang.reflect.Proxy;
import luj.game.server.internal.data.instance.DataTempProxy;

public class DataResultFactory {

  public DataResultFactory(DataTempProxy data, DataResultProxy.FieldHook fieldHook) {
    _data = data;
    _fieldHook = fieldHook;
  }

  public DataResultProxy create() {
    DataResultProxy result = new DataResultProxy(_data, _fieldHook);
    Class<?> dataType = _data.getDataType();

    result._instance = Proxy.newProxyInstance(
        dataType.getClassLoader(), new Class[]{dataType}, result);

    return result;
  }

  private final DataTempProxy _data;

  private final DataResultProxy.FieldHook _fieldHook;
}
