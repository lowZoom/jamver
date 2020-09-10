package luj.game.server.internal.data.load.result;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import luj.game.server.internal.data.instance.DataTempProxy;

//TODO: 预生成代码，没有生成代码的才用这个动态类
public class DataResultProxy implements InvocationHandler {

  public interface FieldHook extends BiConsumer<DataResultProxy, String> {
    // NOOP
  }

  DataResultProxy(DataTempProxy data, FieldHook fieldHook) {
    _data = data;
    _fieldHook = fieldHook;
  }

  public Object getInstance() {
    return checkNotNull(_instance);
  }

  public boolean isDirty() {
    return _dirty;
  }

  public void setDirty(boolean dirty) {
    _dirty = dirty;
  }

  public DataTempProxy getData() {
    return _data;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) {
    String methodName = method.getName();
    _fieldHook.accept(this, methodName);

    return _data.invoke(methodName);
  }

  Object _instance;
  private boolean _dirty;

  private final DataTempProxy _data;
  private final FieldHook _fieldHook;
}
