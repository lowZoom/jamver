package luj.game.server.internal.data.save.update;

import java.util.Set;
import luj.game.server.api.plugin.JamverDataSaveUpdate;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgSet;

final class CSetImpl implements JamverDataSaveUpdate.SetChanged {

  @Override
  public Set<Object> added() {
    return _set.getAddHistory();
  }

  @Override
  public Set<Object> removed() {
    return _set.getRemoveHistory();
  }

  DUpdateMsgSet _set;
}
