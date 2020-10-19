package luj.game.server.internal.data.save.io.invoke;

import java.util.Map;
import luj.game.server.api.plugin.JamverDataSaveIo;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DataUpdateMsg;

final class ChangedImpl implements JamverDataSaveIo.Changed {

  @Override
  public Class<?> dataType() {
    return _msg.getDataType();
  }

  @Override
  public JamverDataSaveIo.Id dataId() {
    return _id;
  }

  @Override
  public Map<String, Object> primitive() {
    return _msg.getPrimitiveUpdated();
  }

  @Override
  public Map<String, JamverDataSaveIo.SetChanged> set() {
    return _set;
  }

  @Override
  public Map<String, JamverDataSaveIo.MapChanged> map() {
    return _map;
  }

  DataUpdateMsg _msg;
  JamverDataSaveIo.Id _id;

  Map<String, JamverDataSaveIo.SetChanged> _set;
  Map<String, JamverDataSaveIo.MapChanged> _map;
}
