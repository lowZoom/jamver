package luj.game.server.internal.data.load.io;

import luj.game.server.api.plugin.JamverDataLoadLoad;

final class DataRequestImpl implements JamverDataLoadLoad.Data {

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
    return _idField;
  }

  private final Class<?> _type;

  private final Comparable<?> _id;
  private final String _idField;
}
