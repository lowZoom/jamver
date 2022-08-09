package luj.game.server.internal.data.execute.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import luj.bean.api.BeanContext;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.Tellable;
import luj.config.api.container.ConfigContainer;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.internal.data.command.queue.element.GroupReqElement;
import luj.game.server.internal.data.execute.finish.ExecDataFinisherV2;
import luj.game.server.internal.data.execute.load.request.node.find.finish.lock.DataPair;
import luj.game.server.internal.data.id.state.DataIdGenState;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroupExecFinisher {

  public GroupExecFinisher(GameDataCommandGroup cmdGroup, List<GroupReqElement> elemList,
      CacheContainer dataCache, DataIdGenState idGenState, ConfigContainer configs,
      Tellable dataRef, Tellable saveRef, Tellable eventRef, ServerMessageHandler.Server remoteRef,
      Map<String, GameplayDataActor.CommandKit> commandMap, BeanContext lujbean) {
    _cmdGroup = cmdGroup;
    _elemList = elemList;
    _dataCache = dataCache;
    _idGenState = idGenState;
    _configs = configs;
    _dataRef = dataRef;
    _saveRef = saveRef;
    _eventRef = eventRef;
    _remoteRef = remoteRef;
    _commandMap = commandMap;
    _lujbean = lujbean;
  }

  /**
   * @see luj.game.server.internal.data.execute.finish.CommandExecFinisher#finish
   */
  public void finish() {
    List<DataEntity> createLog = new ArrayList<>();
    Map<String, DataPair> loadLog = new HashMap<>();

    // 真正执行cmd逻辑
    try {
      new CmdGroupExecutor(_cmdGroup, _elemList, createLog, loadLog, _dataCache,
          _idGenState, _configs, _dataRef, _eventRef, _remoteRef, _commandMap, _lujbean).execute();

    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      //TODO: 出错的时候要清除修改
    }

    String idField = _idGenState.getIdField();
    ExecDataFinisherV2.get(_dataCache, _saveRef, idField, createLog, loadLog).finish();
  }

  private static final Logger LOG = LoggerFactory.getLogger(GroupExecFinisher.class);

  private final GameDataCommandGroup _cmdGroup;
  private final List<GroupReqElement> _elemList;

  private final CacheContainer _dataCache;
  private final DataIdGenState _idGenState;
  private final ConfigContainer _configs;

  private final Tellable _dataRef;
  private final Tellable _saveRef;

  private final Tellable _eventRef;
  private final ServerMessageHandler.Server _remoteRef;
  private final Map<String, GameplayDataActor.CommandKit> _commandMap;

  private final BeanContext _lujbean;
}
