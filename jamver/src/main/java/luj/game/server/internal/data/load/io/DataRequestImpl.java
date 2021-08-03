package luj.game.server.internal.data.load.io;

import static com.google.common.base.Preconditions.checkNotNull;

import luj.game.server.api.plugin.JamverDataLoadIo;

final class DataRequestImpl implements JamverDataLoadIo.Data {

  DataRequestImpl(Class<?> type, Comparable<?> id, String idField) {
    _type = type;
    _id = id;
    _idField = idField;
  }

  @Override
  public Class<?> getType() {
    return _type;
  }

  @Override
  public Comparable<?> getId() {
    return _id;
  }

  @Override
  public String getIdField() {
    return checkNotNull(_idField, "idField");
  }

  private final Class<?> _type;

  private final Comparable<?> _id;
  private final String _idField;
}
