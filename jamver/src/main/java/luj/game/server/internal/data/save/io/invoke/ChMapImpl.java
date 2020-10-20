package luj.game.server.internal.data.save.io.invoke;

import java.util.Map;
import java.util.Set;
import luj.game.server.api.plugin.JamverDataSaveIo;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgMap;

final class ChMapImpl implements JamverDataSaveIo.MapChanged {

  @Override
  public Map<Object, Object> updated() {
    return _msg.getUpdateHistory();
  }

  @Override
  public Set<Object> removed() {
    return _msg.getRemoveHistory();
  }

  DUpdateMsgMap _msg;
}
