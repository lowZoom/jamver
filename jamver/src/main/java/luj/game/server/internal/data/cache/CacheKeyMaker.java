package luj.game.server.internal.data.cache;

public class CacheKeyMaker {

  public CacheKeyMaker(Class<?> dataType, Object dataId) {
    _dataType = dataType;
    _dataId = dataId;
  }

  public String make() {
    return _dataType.getName() + "#" + _dataId;
  }

  private final Class<?> _dataType;

  private final Object _dataId;
}
