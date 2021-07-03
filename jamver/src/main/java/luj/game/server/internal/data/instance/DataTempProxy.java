package luj.game.server.internal.data.instance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import luj.game.server.internal.data.types.map.history.MapWithHistory;

/**
 * @see luj.game.server.internal.data.instancev2.DataEntity
 * @deprecated 直接用map，没必要中间还加层代理，代理只在Load里加就行
 */
@Deprecated
public class DataTempProxy implements InvocationHandler {

  public static final Long GLOBAL = -1L;
  public static final String ID = "id";

  DataTempProxy(Class<?> dataType, Map<String, Object> dataMap,
      MapWithHistory<String, Object> dataMapV2) {
    _dataType = dataType;
    _dataMap = dataMap;
    _dataMapV2 = dataMapV2;
  }

  public DataTempProxy init() {
    _instance = Proxy.newProxyInstance(_dataType.getClassLoader(), new Class[]{_dataType}, this);
    return this;
  }

  public Object getInstance() {
    return _instance;
  }

  public Class<?> getDataType() {
    return _dataType;
  }

  public Map<String, Object> getDataMap() {
    return _dataMap;
  }

  public MapWithHistory<String, Object> getDataMapV2() {
    return _dataMapV2;
  }

  public Object invoke(String methodName) {
    if ("toString".equals(methodName)) {
      return toString();
    }
    return _dataMap.get(methodName);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) {
    return invoke(method.getName());
  }

  @Override
  public String toString() {
    return _dataMap.toString();
  }

  private Object _instance;

  private final Class<?> _dataType;

  @Deprecated
  private final Map<String, Object> _dataMap;

  private final MapWithHistory<String, Object> _dataMapV2;
}
