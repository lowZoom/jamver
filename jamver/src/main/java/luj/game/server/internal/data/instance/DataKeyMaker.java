package luj.game.server.internal.data.instance;

public class DataKeyMaker {

  public DataKeyMaker(Class<?> dataType, Object dataId) {
    _dataType = dataType;
    _dataId = dataId;
  }

  public String make() {
    return _dataType.getName() + "#" + _dataId;
  }

  private final Class<?> _dataType;

  private final Object _dataId;
}
