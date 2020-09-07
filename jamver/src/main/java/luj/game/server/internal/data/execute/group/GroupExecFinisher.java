package luj.game.server.internal.data.execute.group;

import java.util.ArrayList;
import java.util.List;
import luj.bean.api.BeanContext;
import luj.cache.api.container.CacheContainer;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.internal.data.command.queue.element.GroupReqElement;
import luj.game.server.internal.data.execute.save.CommandSaveRequestor;
import luj.game.server.internal.data.instance.DataTempAdder;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.load.result.DataResultProxy;

public class GroupExecFinisher {

  public GroupExecFinisher(GameDataCommandGroup cmdGroup,
      List<GroupReqElement> elemList, CacheContainer dataCache,
      Tellable dataRef, Tellable saveRef, ServerMessageHandler.Server remoteRef,
      BeanContext lujbean) {
    _cmdGroup = cmdGroup;
    _elemList = elemList;
    _dataCache = dataCache;
    _dataRef = dataRef;
    _saveRef = saveRef;
    _remoteRef = remoteRef;
    _lujbean = lujbean;
  }

  public void finish() {
    List<DataTempProxy> createLog = new ArrayList<>();
    List<DataResultProxy> loadLog = new ArrayList<>();

    new CmdGroupExecutor(_cmdGroup, _elemList, createLog,
        loadLog, _dataCache, _dataRef, _remoteRef, _lujbean).execute();

    for (DataTempProxy data : createLog) {
      new DataTempAdder(_dataCache, data.getDataType(), data).add();
    }
    new CommandSaveRequestor(_saveRef, createLog, loadLog).request();
  }

  private final GameDataCommandGroup _cmdGroup;
  private final List<GroupReqElement> _elemList;

  private final CacheContainer _dataCache;
  private final Tellable _dataRef;
  private final Tellable _saveRef;

  private final ServerMessageHandler.Server _remoteRef;
  private final BeanContext _lujbean;
}
