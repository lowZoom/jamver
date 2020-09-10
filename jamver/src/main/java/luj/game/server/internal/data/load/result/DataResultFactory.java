package luj.game.server.internal.data.load.result;

import java.lang.reflect.Proxy;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.load.result.dirty.DirtyMarkable;
import luj.game.server.internal.data.types.HasOp;

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

    Runnable dirtyMarker = () -> result.setDirty(true);
    _data.getDataMap().values().stream()
        .filter(d -> d instanceof HasOp)
        .map(d -> ((HasOp) d).<DirtyMarkable>getDataOp())
        .forEach(m -> m.setDirtyMarker(dirtyMarker));

    return result;
  }

  private final DataTempProxy _data;

  private final DataResultProxy.FieldHook _fieldHook;
}
