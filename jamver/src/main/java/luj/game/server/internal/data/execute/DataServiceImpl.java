package luj.game.server.internal.data.execute;

import java.time.Duration;
import java.util.function.Supplier;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.data.instance.DataInstanceCreator;
import luj.game.server.internal.data.instance.DataTempAdder;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.execute.DatacmdExecMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class DataServiceImpl implements GameDataCommand.Data {

  DataServiceImpl(ActorMessageHandler.Ref dataRef, CacheContainer dataCache) {
    _dataRef = dataRef;
    _dataCache = dataCache;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T create(Class<T> dataType) {
    DataTempProxy dataObj = new DataInstanceCreator(dataType).create();
    new DataTempAdder(_dataCache, dataType, dataObj).add();
    return (T) dataObj.getInstance();
  }

  @Override
  public <T> void set(Supplier<T> field, T value) {
    LOG.warn("尚未实现：set");
  }

  @Override
  public <P> void executeCommand(Class<? extends GameDataCommand<P, ?>> commandType) {
    executeCommand(commandType, null);
  }

  @Override
  public <P> void executeCommand(Class<? extends GameDataCommand<P, ?>> commandType, P param) {
    DatacmdExecMsg msg = new DatacmdExecMsg(commandType, param);
    _dataRef.tell(msg, Duration.ZERO);
  }

  private static final Logger LOG = LoggerFactory.getLogger(DataServiceImpl.class);

  private final ActorMessageHandler.Ref _dataRef;

  @Deprecated
  private final CacheContainer _dataCache;
}
