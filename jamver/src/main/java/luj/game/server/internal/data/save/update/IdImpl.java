package luj.game.server.internal.data.save.update;

import luj.game.server.api.plugin.JamverDataSaveUpdate;

final class IdImpl implements JamverDataSaveUpdate.Id {

  IdImpl(Comparable<?> idValue, String fieldName) {
    _idValue = idValue;
    _fieldName = fieldName;
  }

  @Override
  public Object getValue() {
    return _idValue;
  }

  @Override
  public String getField() {
    return _fieldName;
  }

  private final Comparable<?> _idValue;

  private final String _fieldName;
}
