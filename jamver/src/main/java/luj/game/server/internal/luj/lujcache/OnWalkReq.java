package luj.game.server.internal.luj.lujcache;

import luj.ava.spring.Internal;
import luj.cache.api.request.RequestWalkListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnWalkReq implements RequestWalkListener {

  @Override
  public void onWalk(Context ctx) {
    Class<?> dataType = ctx.getDataType();
    Long dataId = ctx.getDataId();

    LOG.debug("读取读取读取读取：{}, {}", dataType.getName(), dataId);
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnWalkReq.class);
}
