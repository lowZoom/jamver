package luj.game.server.internal.data.execute.group;

import java.util.List;
import java.util.stream.Collectors;
import luj.bean.api.BeanContext;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.internal.data.command.queue.element.GroupReqElement;
import luj.game.server.internal.data.execute.DataServiceImpl;
import luj.game.server.internal.data.execute.finish.ExecFinishWalker;
import luj.game.server.internal.data.execute.finish.LoadResultProxy;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class CmdGroupExecutor {

  public CmdGroupExecutor(GameDataCommandGroup cmdGroup, List<GroupReqElement> elemList,
      List<DataTempProxy> createLog, List<DataTempProxy> loadLog, CacheContainer dataCache,
      Tellable dataRef, ServerMessageHandler.Server remoteRef, BeanContext lujbean) {
    _cmdGroup = cmdGroup;
    _elemList = elemList;
    _createLog = createLog;
    _loadLog = loadLog;
    _dataCache = dataCache;
    _dataRef = dataRef;
    _remoteRef = remoteRef;
    _lujbean = lujbean;
  }

  public void execute() {
    ContextImpl ctx = new ContextImpl();

    ctx._elemList = _elemList.stream()
        .map(this::makeElem)
        .collect(Collectors.toList());

    try {
      _cmdGroup.onExecute(ctx);

    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new UnsupportedOperationException(e);
    }
  }

  private ElementImpl<?> makeElem(GroupReqElement e) {
    ElementImpl<?> result = new ElementImpl<>();
    GameplayDataActor.CommandKit cmdKit = e.getCommandKit();

    result._cmdKit = cmdKit;
    result._cmdParam = e.getCommandParam();
    result._remoteRef = _remoteRef;
    result._lujbean = _lujbean;

    LoadResultProxy resultProxy = LoadResultProxy.create(cmdKit.getLoadResultType());
    DataServiceImpl dataSvc = new DataServiceImpl(_dataRef, _createLog, _remoteRef);
    e.getCacheReq().walk(new ExecFinishWalker(
        _dataCache, resultProxy, _loadLog, dataSvc::specifySetField));

    result._resultProxy = resultProxy;
    result._dataSvc = dataSvc;

    return result;
  }

  private final GameDataCommandGroup _cmdGroup;
  private final List<GroupReqElement> _elemList;

  private final List<DataTempProxy> _createLog;
  private final List<DataTempProxy> _loadLog;

  private final CacheContainer _dataCache;
  private final Tellable _dataRef;

  private final ServerMessageHandler.Server _remoteRef;
  private final BeanContext _lujbean;
}
