package luj.game.server.internal.data.save.wait;

import java.util.Map;

public class IoWaitBatch {

  public IoWaitBatch(Map<Comparable<?>, BatchCreateItem> createMap,
      Map<Comparable<?>, BatchUpdateItem> updateMap) {
    _createMap = createMap;
    _updateMap = updateMap;
  }

  public Map<Comparable<?>, BatchCreateItem> getCreateMap() {
    return _createMap;
  }

  public Map<Comparable<?>, BatchUpdateItem> getUpdateMap() {
    return _updateMap;
  }

  private final Map<Comparable<?>, BatchCreateItem> _createMap;

  private final Map<Comparable<?>, BatchUpdateItem> _updateMap;
}
