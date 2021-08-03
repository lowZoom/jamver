package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.load;

import static com.google.common.base.Preconditions.checkState;

import java.util.Map;
import luj.ava.spring.Internal;
import luj.game.server.internal.data.load.cache.CacheLoadFinisher;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnLoadFinish implements GameplayDataActor.Handler<LoadFinishMsg> {

  @Override
  public void onHandle(Context ctx) {
    GameplayDataActor self = ctx.getActorState(this);
    LoadFinishMsg msg = ctx.getMessage(this);

    Class<?> dataType = msg.getDataType();
    Comparable<?> dataId = msg.getDataId();

    boolean present = msg.isPresent();
    LOG.debug("[game]数据读取完成：{}#{}, {}", dataType.getName(), dataId, present);

    Map<String, Object> valueMap = msg.getDataValue();
    checkState(present == !valueMap.isEmpty());

    new CacheLoadFinisher(dataType, dataId, present, valueMap,
        self, self.getIdGenState(), ctx.getActorRef(), self.getSaveRef()).finish();
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnLoadFinish.class);
}
