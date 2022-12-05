package luj.game.server.internal.data.execute;

import luj.bean.api.BeanContext;
import luj.cluster.api.actor.Tellable;
import luj.config.api.container.ConfigContainer;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.data.execute.service.event.EventServiceFactory;
import luj.game.server.internal.data.execute.service.proto.ProtoServiceFactory;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class DataCmdExecutor {

  public DataCmdExecutor(GameplayDataActor.CommandKit cmdKit, Object param, Object loadResult,
      GameDataCommand.Data dataSvc, Tellable eventRef, ConfigContainer configs,
      BeanContext lujbean) {
    _cmdKit = cmdKit;
    _param = param;
    _loadResult = loadResult;
    _dataSvc = dataSvc;
    _eventRef = eventRef;
    _configs = configs;
    _lujbean = lujbean;
  }

  public void execute() throws Exception {
    DataCmdServiceImpl commandSvc = new DataCmdServiceImpl();
    commandSvc._dataSvc = _dataSvc;
    commandSvc._configs = _configs;

    commandSvc._eventSvc = new EventServiceFactory(_eventRef, _lujbean);
    commandSvc._protoSvc = ProtoServiceFactory.GET.create(_lujbean);

    _cmdKit.getCommand().onExecute(new CmdContextImpl(
        _param, _loadResult, _cmdKit.getLogger(), commandSvc));
  }

  private final GameplayDataActor.CommandKit _cmdKit;
  private final Object _param;
  private final Object _loadResult;

  private final GameDataCommand.Data _dataSvc;
  private final Tellable _eventRef;
  private final ConfigContainer _configs;

  private final BeanContext _lujbean;
}
