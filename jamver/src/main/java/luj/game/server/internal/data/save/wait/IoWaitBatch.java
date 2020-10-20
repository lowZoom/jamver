package luj.game.server.internal.data.save.wait;

import java.util.List;
import java.util.Map;

public class IoWaitBatch {

  public IoWaitBatch(List<BatchCreateItem> createList,
      Map<Comparable<?>, BatchUpdateItem> updateMap) {
    _createList = createList;
    _updateMap = updateMap;
  }

  public List<BatchCreateItem> getCreateList() {
    return _createList;
  }

  public Map<Comparable<?>, BatchUpdateItem> getUpdateMap() {
    return _updateMap;
  }

  private final List<BatchCreateItem> _createList;

  private final Map<Comparable<?>, BatchUpdateItem> _updateMap;
}
