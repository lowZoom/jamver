package luj.game.server.internal.data.execute.finish;

import java.util.ArrayList;
import java.util.List;
import luj.bean.api.BeanContext;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.CacheRequest;
import luj.cluster.api.actor.Tellable;
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

  public CommandExecFinisher(Class<?> loadResultType, CacheRequest cacheReq,
      CacheContainer dataCache, DataIdGenState idGenState, String cmdType,
      GameplayDataActor.CommandKit commandKit, Object cmdParam, Tellable dataRef, Tellable saveRef,
      ServerMessageHandler.Server remoteRef, BeanContext lujbean) {
    _loadResultType = loadResultType;
    _cacheReq = cacheReq;
    _dataCache = dataCache;
    _idGenState = idGenState;
    _cmdType = cmdType;
    _commandKit = commandKit;
    _cmdParam = cmdParam;
    _dataRef = dataRef;
    _saveRef = saveRef;
    _remoteRef = remoteRef;
    _lujbean = lujbean;
  }

  public void finish() {
//    LOG.debug("[game]执行数据CMD：{} {}", _cmdType, _cmdParam);
    LOG.debug("[game]执行数据CMD：{}", _cmdType);

    List<DataEntity> createLog = new ArrayList<>();
    List<DataEntity> loadLog = new ArrayList<>();

    LoadResultProxy resultProxy = LoadResultProxy.create(_loadResultType);
    DataServiceImpl dataSvc = new DataServiceImpl(
        _idGenState, _dataRef, createLog, _remoteRef, _commandKit.getParentMap(), _lujbean);

    _cacheReq.walk(new ExecFinishWalker(
        _dataCache, resultProxy, loadLog, dataSvc::specifySetField));

    Object loadResult = resultProxy.getInstance();
    GameDataCommand.Network netSvc = new NetServiceFactory(_remoteRef).create();

    // 真正调用外部cmd逻辑
    //TODO: 出错的时候要清除修改
    new DataCmdExecutor(_commandKit, _cmdParam, loadResult, dataSvc, netSvc, _lujbean).execute();

    String idField = _idGenState.getIdField();
    new ExecDataFinisherV2(_dataCache, _saveRef, idField, createLog, loadLog).finish();
  }

  private static final Logger LOG = LoggerFactory.getLogger(CommandExecFinisher.class);

  private final Class<?> _loadResultType;
  private final CacheRequest _cacheReq;

  private final CacheContainer _dataCache;
  private final DataIdGenState _idGenState;

  private final String _cmdType;
  private final GameplayDataActor.CommandKit _commandKit;
  private final Object _cmdParam;

  private final Tellable _dataRef;
  private final Tellable _saveRef;

  private final ServerMessageHandler.Server _remoteRef;
  private final BeanContext _lujbean;
}
