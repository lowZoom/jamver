package luj.game.server.internal.data.save;

import luj.game.server.api.data.annotation.Transient;
import luj.game.server.internal.data.instance.DataTempProxy;

public enum DataTransientChecker {
  GET;

  public boolean check(DataTempProxy dataObj) {
    Class<?> dataType = dataObj.getDataType();
    return dataType.isAnnotationPresent(Transient.class);
  }
}
