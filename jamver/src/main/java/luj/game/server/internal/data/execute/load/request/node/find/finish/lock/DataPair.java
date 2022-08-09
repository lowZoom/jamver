package luj.game.server.internal.data.execute.load.request.node.find.finish.lock;

import luj.game.server.internal.data.instancev2.DataEntity;

public class DataPair {

  public DataPair(DataEntity data, String key) {
    _data = data;
    _key = key;
  }

  public DataEntity getData() {
    return _data;
  }

  public String getKey() {
    return _key;
  }

  private final DataEntity _data;

  private final String _key;
}
