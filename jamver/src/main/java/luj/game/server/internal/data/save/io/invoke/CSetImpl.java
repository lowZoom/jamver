package luj.game.server.internal.data.save.io.invoke;

import java.util.Set;
import luj.game.server.api.plugin.JamverDataSaveIo;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgSet;

final class CSetImpl implements JamverDataSaveIo.SetChanged {

  @Override
  public Set<Object> added() {
    return _msg.getAddHistory();
  }

  @Override
  public Set<Object> removed() {
    return _msg.getRemoveHistory();
  }

  DUpdateMsgSet _msg;
}
