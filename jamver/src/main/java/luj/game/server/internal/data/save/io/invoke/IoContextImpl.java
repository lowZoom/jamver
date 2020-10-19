package luj.game.server.internal.data.save.io.invoke;

import java.util.Collection;
import java.util.List;
import luj.game.server.api.plugin.JamverDataSaveIo;

final class IoContextImpl implements JamverDataSaveIo.Context {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getSaveState(JamverDataSaveIo<T> plugin) {
    return (T) _saveState;
  }

  @Override
  public Collection<JamverDataSaveIo.Created> getCreated() {
    return _created;
  }

  @Override
  public Collection<JamverDataSaveIo.Changed> getChanged() {
    return _changed;
  }

  Object _saveState;

  List<JamverDataSaveIo.Created> _created;
  List<JamverDataSaveIo.Changed> _changed;
}
