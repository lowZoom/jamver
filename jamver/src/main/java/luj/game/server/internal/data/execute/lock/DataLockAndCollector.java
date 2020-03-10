package luj.game.server.internal.data.execute.lock;

import java.util.HashMap;

public class DataLockAndCollector {

  public DataLockAndCollector(Class<?> resultType) {
    _resultType = resultType;
  }

  public Object lockAndCollect() {
    return new LoadResultProxy(_resultType, new HashMap<>()).newInstance();
  }

  private final Class<?> _resultType;
}
