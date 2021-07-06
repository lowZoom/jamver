package luj.game.server.internal.data.cache;

public class CacheKeyMaker {

  public static CacheKeyMaker create(Class<?> dataType, Object dataId) {
    return new CacheKeyMaker(dataType.getName(), dataId);
  }

  public CacheKeyMaker(String dataType, Object dataId) {
    _dataType = dataType;
    _dataId = dataId;
  }

  public String make() {
    return _dataType + "#" + _dataId;
  }

  private final String _dataType;

  private final Object _dataId;
}
