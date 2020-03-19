package luj.game.server.internal.data.execute.load.missing;

final class MissingImpl implements LoadMissingCollector.Missing {

  MissingImpl(Class<?> dataType, Comparable<?> dataId) {
    _dataType = dataType;
    _dataId = dataId;
  }

  @Override
  public Class<?> dataType() {
    return _dataType;
  }

  @Override
  public Comparable<?> dataId() {
    return _dataId;
  }

  private final Class<?> _dataType;

  private final Comparable<?> _dataId;
}
