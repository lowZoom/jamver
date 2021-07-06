package luj.game.server.internal.data.execute.load;

import luj.cache.api.request.CacheRequest;
import luj.game.server.internal.data.load.result.LoadResultProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ReqRootTransientAppender {
  GET;

  public CacheRequest.Node append(CacheRequest cacheReq, Class<?> dataType, Comparable<?> dataId) {
    return cacheReq.getRoot().addChild(dataType, dataId, this::setResultField);
  }

  private void setResultField(LoadResultProxy loadResult, Object value) {
//    LOG.debug("临时变量：{}", _dataType.getName());
  }

//  private static final Logger LOG = LoggerFactory.getLogger(ReqRootTransientAppender.class);
}
