package luj.game.server.internal.data.execute.load.missing.log;

import java.util.Map;
import luj.game.server.internal.data.execute.load.missing.DataReadyChecker;

public enum MissingLogAdder {
  GET;

  public void add(MissingLog log, Class<?> dataType, Comparable<?> dataId) {
    Map<String, DataReadyChecker.Missing> logMap = log.getLogMap();
    String itemKey = dataType.getName() + "_" + dataId;
    if (logMap.containsKey(itemKey)) {
      return;
    }

    MissingImpl item = new MissingImpl();
    item._dataType = dataType;
    item._dataId = dataId;

    logMap.put(itemKey, item);
  }
}
