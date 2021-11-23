package luj.game.server.internal.data.execute.load.missing.log;

import luj.game.server.internal.data.execute.load.missing.DataReadyChecker;

final class MissingImpl implements DataReadyChecker.Missing {

  @Override
  public Class<?> dataType() {
    return _dataType;
  }

  @Override
  public Comparable<?> dataId() {
    return _dataId;
  }

  Class<?> _dataType;

  Comparable<?> _dataId;
}
