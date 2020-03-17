package luj.game.server.internal.luj.lujcluster.actor.gameplay.dataload.load;

public class DataLoadMsg {

  public DataLoadMsg(Class<?> dataType, Comparable<?> dataId, String idField) {
    _dataType = dataType;
    _dataId = dataId;
    _idField = idField;
  }

  public Class<?> getDataType() {
    return _dataType;
  }

  public Comparable<?> getDataId() {
    return _dataId;
  }

  public String getIdField() {
    return _idField;
  }

  private final Class<?> _dataType;

  private final Comparable<?> _dataId;
  private final String _idField;
}
