package luj.game.server.internal.data.save.io.invoke;

import luj.game.server.api.plugin.JamverDataSaveIo;

final class IdImpl implements JamverDataSaveIo.Id {

  @Override
  public Comparable<?> value() {
    return _dataId;
  }

  @Override
  public String fieldName() {
    return _idField;
  }

  Comparable<?> _dataId;

  String _idField;
}
