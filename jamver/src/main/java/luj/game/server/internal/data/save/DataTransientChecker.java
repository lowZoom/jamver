package luj.game.server.internal.data.save;

import luj.game.server.api.data.annotation.Transient;
import luj.game.server.internal.data.instance.DataTempProxy;

public class DataTransientChecker {

  public DataTransientChecker(DataTempProxy dataObj) {
    _dataObj = dataObj;
  }

  public boolean check() {
    Class<?> dataType = _dataObj.getDataType();
    return dataType.isAnnotationPresent(Transient.class);
  }

  private final DataTempProxy _dataObj;
}
