package luj.game.server.internal.data.save.io.invoke;

import java.util.Map;
import java.util.Set;
import luj.game.server.api.plugin.JamverDataSaveIo;
import luj.game.server.internal.data.save.wait.BatchCreateItem;

final class CreatedImpl implements JamverDataSaveIo.Created {

  @Override
  public String dataType() {
    return _item.getDataType();
  }

  @Override
  public JamverDataSaveIo.Id dataId() {
    return _id;
  }

  @Override
  public Map<String, Object> primitive() {
    return _primitive;
  }

  @Override
  public Map<String, Set<Object>> set() {
    return _set;
  }

  @Override
  public Map<String, Map<Object, Object>> map() {
    return _map;
  }

  BatchCreateItem _item;

  JamverDataSaveIo.Id _id;
  Map<String, Object> _primitive;

  Map<String, Set<Object>> _set;
  Map<String, Map<Object, Object>> _map;
}
