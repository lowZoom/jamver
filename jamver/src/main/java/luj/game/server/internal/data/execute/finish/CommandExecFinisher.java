package luj.game.server.internal.data.execute.finish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.CacheRequest;
import luj.cluster.api.actor.ActorMessageHandler;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.internal.data.execute.DataCmdExecutor;
import luj.game.server.internal.data.execute.DataServiceImpl;
import luj.game.server.internal.data.execute.save.CommandSaveRequestor;
import luj.game.server.internal.data.instance.DataTempAdder;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.load.result.DataResultProxy;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandExecFinisher {

  public CommandExecFinisher(Class<?> loadResultType, CacheRequest cacheReq,
      CacheContainer dataCache, Class<?> cmdType,
      GameplayDataActor.CommandKit commandKit, Object cmdParam,
      ActorMessageHandler.Ref dataRef, ActorPreStartHandler.Actor saveRef) {
    _loadResultType = loadResultType;
    _cacheReq = cacheReq;
    _dataCache = dataCache;
    _cmdType = cmdType;
    _commandKit = commandKit;
    _cmdParam = cmdParam;
    _dataRef = dataRef;
    _saveRef = saveRef;
  }

  public void finish() {
//    ctx.getLogger().debug("执行数据CMD：{}", cmdType.getName());
    LOG.debug("[game]执行数据CMD：{}", _cmdType.getName());

    List<DataTempProxy> createLog = new ArrayList<>();
    DataServiceImpl dataSvc = new DataServiceImpl(_dataRef, createLog);

    LoadResultProxy resultProxy = new LoadResultProxy(_loadResultType, new HashMap<>()).init();
    List<DataResultProxy> loadLog = new ArrayList<>();
    _cacheReq.walk(new FinishWalker(_dataCache, resultProxy, loadLog, dataSvc::specifySetField));

    Object loadResult = resultProxy.getInstance();
    new DataCmdExecutor(_commandKit, _cmdParam, loadResult, dataSvc).execute();

    for (DataTempProxy data : createLog) {
      new DataTempAdder(_dataCache, data.getDataType(), data).add();
    }
    new CommandSaveRequestor(_saveRef, createLog, loadLog).request();
  }

  private static final Logger LOG = LoggerFactory.getLogger(CommandExecFinisher.class);

  private final Class<?> _loadResultType;

  private final CacheRequest _cacheReq;
  private final CacheContainer _dataCache;

  private final Class<?> _cmdType;
  private final GameplayDataActor.CommandKit _commandKit;
  private final Object _cmdParam;

  private final ActorMessageHandler.Ref _dataRef;
  private final ActorPreStartHandler.Actor _saveRef;
}
