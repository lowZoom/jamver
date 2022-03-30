package luj.game.server.internal.data.execute.finish;

import java.util.ArrayList;
import java.util.List;
import luj.bean.api.BeanContext;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.CacheRequest;
import luj.cluster.api.actor.Tellable;
import luj.config.api.container.ConfigContainer;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.data.execute.DataCmdExecutor;
import luj.game.server.internal.data.execute.service.data.DataServiceImpl;
import luj.game.server.internal.data.execute.service.network.NetServiceFactory;
import luj.game.server.internal.data.id.state.DataIdGenState;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.load.result.LoadResultProxy;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandExecFinisher {

  public CommandExecFinisher(GameplayDataActor.CommandKit commandKit, Object cmdParam,
      CacheRequest cacheReq, CacheContainer dataCache, DataIdGenState idGenState,
      ConfigContainer configs, Tellable dataRef, Tellable saveRef,
      ServerMessageHandler.Server remoteRef, BeanContext lujbean) {
    _commandKit = commandKit;
    _cmdParam = cmdParam;
    _cacheReq = cacheReq;
    _dataCache = dataCache;
    _idGenState = idGenState;
    _configs = configs;
    _dataRef = dataRef;
    _saveRef = saveRef;
    _remoteRef = remoteRef;
    _lujbean = lujbean;
  }

  public void finish() {
    List<DataEntity> createLog = new ArrayList<>();
    List<DataEntity> loadLog = new ArrayList<>();

    Class<?> loadResultType = _commandKit.getLoadResultType();
    LoadResultProxy resultProxy = LoadResultProxy.create(loadResultType);
    DataServiceImpl dataSvc = new DataServiceImpl(
        _idGenState, _dataRef, createLog, _remoteRef, _commandKit.getParentMap(), _lujbean);

    // 收集并锁定要用的数据
    _cacheReq.walk(new ExecFinishWalker(
        _dataCache, resultProxy, loadLog, dataSvc::specifySetField));

    Object loadResult = resultProxy.getInstance();
    GameDataCommand.Network netSvc = new NetServiceFactory(_remoteRef).create();

//    LOG.debug("[game]执行数据CMD：{} {}", _cmdType, _cmdParam);
    LOG.debug("[game]执行数据CMD：{}", _commandKit.getCommandType().getName());

    // 真正调用外部cmd逻辑
    try {
      //TODO: 并发到另外的线程去执行
      new DataCmdExecutor(_commandKit, _cmdParam,
          loadResult, dataSvc, netSvc, _configs, _lujbean).execute();

    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      //TODO: 出错的时候要清除修改
    }

    String idField = _idGenState.getIdField();
    new ExecDataFinisherV2(_dataCache, _saveRef, idField, createLog, loadLog).finish();
  }

  private static final Logger LOG = LoggerFactory.getLogger(CommandExecFinisher.class);

  private final GameplayDataActor.CommandKit _commandKit;
  private final Object _cmdParam;

  private final CacheRequest _cacheReq;
  private final CacheContainer _dataCache;

  private final DataIdGenState _idGenState;
  private final ConfigContainer _configs;

  private final Tellable _dataRef;
  private final Tellable _saveRef;

  private final ServerMessageHandler.Server _remoteRef;
  private final BeanContext _lujbean;
}
