package luj.game.server.internal.data.execute.load.missing.log;

import java.util.Map;
import luj.game.server.internal.data.cache.CacheKeyMaker;
import luj.game.server.internal.data.execute.load.missing.DataReadyChecker;

public enum MissingLogAdder {
  GET;

  public void add(MissingLog log, Class<?> dataType, Comparable<?> dataId) {
    Map<String, DataReadyChecker.Missing> logMap = log.getLogMap();
    String itemKey = CacheKeyMaker.create(dataType, dataId).make();
    if (logMap.containsKey(itemKey)) {
      return;
    }

    var item = new MissingImpl();
    item._dataType = dataType;
    item._dataId = dataId;

//    LOG.debug("缓存项未存在：{}", itemKey);
    logMap.put(itemKey, item);
  }

//  private static final Logger LOG = LoggerFactory.getLogger(MissingLogAdder.class);
}
