package luj.game.server.internal.data.save.update;

import java.util.Map;
import luj.game.server.api.plugin.JamverDataSaveUpdate;

final class ChangedImpl implements JamverDataSaveUpdate.Changed {

  @Override
  public Map<String, Object> primitive() {
    return _primitive;
  }

  @Override
  public Map<String, JamverDataSaveUpdate.SetChanged> set() {
    return _set;
  }

  @Override
  public Map<String, JamverDataSaveUpdate.MapChanged> map() {
    return _map;
  }

  Map<String, Object> _primitive;

  Map<String, JamverDataSaveUpdate.SetChanged> _set;
  Map<String, JamverDataSaveUpdate.MapChanged> _map;
}
