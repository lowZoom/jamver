package luj.game.server.internal.data.save.io.invoke;

import luj.game.server.api.plugin.JamverDataSaveIo;
import luj.game.server.internal.data.save.wait.BatchUpdateItem;

final class IdImpl implements JamverDataSaveIo.Id {

  @Override
  public Object value() {
    return _item.getDataId();
  }

  @Override
  public String fieldName() {
    return _item.getIdField();
  }

  BatchUpdateItem _item;
}
