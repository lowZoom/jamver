package luj.game.server.internal.data.execute.lock;

public class DataLockAndCollector {

  public DataLockAndCollector(Class<?> resultType) {
    _resultType = resultType;
  }

  public Object lockAndCollect() {
    return null;
  }

  private final Class<?> _resultType;
}
