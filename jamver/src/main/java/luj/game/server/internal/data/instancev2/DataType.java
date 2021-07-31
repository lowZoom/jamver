package luj.game.server.internal.data.instancev2;

public class DataType {

  public static DataType create(Class<?> dataClass) {
    DataType type = new DataType(dataClass.getName(), false);
    type._classCache = dataClass;
    return type;
  }

  public DataType(String name, boolean aTransient) {
    _name = name;
    _transient = aTransient;
  }

  public Class<?> getClassCache() {
    return _classCache;
  }

  public void setClassCache(Class<?> classCache) {
    _classCache = classCache;
  }

  public String getName() {
    return _name;
  }

  public boolean isTransient() {
    return _transient;
  }

  private Class<?> _classCache;

  private final String _name;
  private final boolean _transient;
}
