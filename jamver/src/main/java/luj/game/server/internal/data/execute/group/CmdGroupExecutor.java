package luj.game.server.internal.data.execute.group;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import luj.bean.api.BeanContext;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.Tellable;
import luj.config.api.container.ConfigContainer;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.internal.data.command.queue.element.GroupReqElement;
import luj.game.server.internal.data.execute.finish.ExecFinishWalker;
import luj.game.server.internal.data.execute.service.data.DataServiceImpl;
import luj.game.server.internal.data.id.state.DataIdGenState;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.load.result.LoadResultProxy;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class CmdGroupExecutor {

  public CmdGroupExecutor(GameDataCommandGroup cmdGroup, List<GroupReqElement> elemList,
      List<DataEntity> createLog, List<DataEntity> loadLog, CacheContainer dataCache,
      DataIdGenState idGenState, ConfigContainer configs, Tellable dataRef,
      Tellable eventRef, ServerMessageHandler.Server remoteRef,
      Map<String, GameplayDataActor.CommandKit> commandMap, BeanContext lujbean) {
    _cmdGroup = cmdGroup;
    _elemList = elemList;
    _createLog = createLog;
    _loadLog = loadLog;
    _dataCache = dataCache;
    _idGenState = idGenState;
    _configs = configs;
    _dataRef = dataRef;
    _eventRef = eventRef;
    _remoteRef = remoteRef;
    _commandMap = commandMap;
    _lujbean = lujbean;
  }

  public void execute() throws Exception {
    ContextImpl ctx = new ContextImpl();

    ctx._elemList = _elemList.stream()
        .map(this::makeElem)
        .collect(Collectors.toList());

    _cmdGroup.onExecute(ctx);
  }

  private ElementImpl<?> makeElem(GroupReqElement e) {
    GameplayDataActor.CommandKit cmdKit = e.getCommandKit();
    LoadResultProxy resultProxy = LoadResultProxy.create(cmdKit.getLoadResultType());

    DataServiceImpl dataSvc = new DataServiceImpl(
        _idGenState, _dataRef, _createLog, _remoteRef, _commandMap, _lujbean);

    e.getCacheReq().walk(new ExecFinishWalker(
        _dataCache, resultProxy, _loadLog, dataSvc::specifySetField));

    ElementImpl<?> result = new ElementImpl<>();
    result._cmdKit = cmdKit;
    result._cmdParam = e.getCommandParam();
    result._resultProxy = resultProxy;
    result._dataSvc = dataSvc;

    result._configs = _configs;
    result._eventRef = _eventRef;

    result._remoteRef = _remoteRef;
    result._lujbean = _lujbean;

    return result;
  }

  private final GameDataCommandGroup _cmdGroup;
  private final List<GroupReqElement> _elemList;

  private final List<DataEntity> _createLog;
  private final List<DataEntity> _loadLog;

  private final CacheContainer _dataCache;
  private final DataIdGenState _idGenState;
  private final ConfigContainer _configs;

  private final Tellable _dataRef;
  private final Tellable _eventRef;

  private final ServerMessageHandler.Server _remoteRef;
  private final Map<String, GameplayDataActor.CommandKit> _commandMap;

  private final BeanContext _lujbean;
}
