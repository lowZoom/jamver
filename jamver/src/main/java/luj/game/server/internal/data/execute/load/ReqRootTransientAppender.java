package luj.game.server.internal.data.execute.load;

import luj.cache.api.request.CacheRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReqRootTransientAppender {

  public ReqRootTransientAppender(CacheRequest cacheReq, Class<?> dataType, Comparable<?> dataId) {
    _cacheReq = cacheReq;
    _dataType = dataType;
    _dataId = dataId;
  }

  public CacheRequest.Node append() {
    return _cacheReq.getRoot().addChild(_dataType, _dataId, this::setResultField);
  }

  private void setResultField(ResultDataProxy loadResult, Object value) {
//    LOG.debug("临时变量：{}", _dataType.getName());
  }

  private static final Logger LOG = LoggerFactory.getLogger(ReqRootTransientAppender.class);

  private final CacheRequest _cacheReq;

  private final Class<?> _dataType;
  private final Comparable<?> _dataId;
}
