package luj.game.server.internal.data.execute.group;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import luj.bean.api.BeanContext;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.internal.data.command.queue.element.GroupReqElement;
import luj.game.server.internal.data.execute.finish.ExecDataFinisherV2;
import luj.game.server.internal.data.id.state.DataIdGenState;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class GroupExecFinisher {

  public GroupExecFinisher(GameDataCommandGroup cmdGroup, List<GroupReqElement> elemList,
      CacheContainer dataCache, DataIdGenState idGenState, Tellable dataRef, Tellable saveRef,
      ServerMessageHandler.Server remoteRef, Map<String, GameplayDataActor.CommandKit> commandMap,
      BeanContext lujbean) {
    _cmdGroup = cmdGroup;
    _elemList = elemList;
    _dataCache = dataCache;
    _idGenState = idGenState;
    _dataRef = dataRef;
    _saveRef = saveRef;
    _remoteRef = remoteRef;
    _commandMap = commandMap;
    _lujbean = lujbean;
  }

  public void finish() {
    List<DataEntity> createLog = new ArrayList<>();
    List<DataEntity> loadLog = new ArrayList<>();

    // 真正执行cmd逻辑
    new CmdGroupExecutor(_cmdGroup, _elemList, createLog,
        loadLog, _dataCache, _idGenState, _dataRef, _remoteRef, _commandMap, _lujbean).execute();

    String idField = _idGenState.getIdField();
    new ExecDataFinisherV2(_dataCache, _saveRef, idField, createLog, loadLog).finish();
  }

  private final GameDataCommandGroup _cmdGroup;
  private final List<GroupReqElement> _elemList;

  private final CacheContainer _dataCache;
  private final DataIdGenState _idGenState;
  private final Tellable _dataRef;
  private final Tellable _saveRef;

  private final ServerMessageHandler.Server _remoteRef;
  private final Map<String, GameplayDataActor.CommandKit> _commandMap;

  private final BeanContext _lujbean;
}
