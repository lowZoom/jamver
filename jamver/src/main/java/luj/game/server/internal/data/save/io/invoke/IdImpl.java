package luj.game.server.internal.data.save.io.invoke;

import luj.game.server.api.plugin.JamverDataSaveIo;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DataUpdateMsg;

final class IdImpl implements JamverDataSaveIo.Id {

  @Override
  public Object value() {
    return _msg.getDataId();
  }

  @Override
  public String fieldName() {
    return _msg.getIdField();
  }

  DataUpdateMsg _msg;
}
