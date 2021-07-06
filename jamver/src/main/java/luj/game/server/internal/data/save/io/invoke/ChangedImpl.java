package luj.game.server.internal.data.save.io.invoke;

import java.util.Map;
import luj.game.server.api.plugin.JamverDataSaveIo;
import luj.game.server.internal.data.save.wait.BatchUpdateItem;

final class ChangedImpl implements JamverDataSaveIo.Changed {

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
    return _item.getPrimitiveUpdated();
  }

  @Override
  public Map<String, JamverDataSaveIo.SetChanged> set() {
    return _set;
  }

  @Override
  public Map<String, JamverDataSaveIo.MapChanged> map() {
    return _map;
  }

  BatchUpdateItem _item;
  JamverDataSaveIo.Id _id;

  Map<String, JamverDataSaveIo.SetChanged> _set;
  Map<String, JamverDataSaveIo.MapChanged> _map;
}
