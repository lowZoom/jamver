package luj.game.server.internal.data.execute;

import luj.bean.api.BeanContext;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class DataCmdExecutor {

  public DataCmdExecutor(GameplayDataActor.CommandKit cmdKit, Object param, Object loadResult,
      GameDataCommand.Data dataSvc, GameDataCommand.Network netSvc, BeanContext lujbean) {
    _cmdKit = cmdKit;
    _param = param;
    _loadResult = loadResult;
    _dataSvc = dataSvc;
    _netSvc = netSvc;
    _lujbean = lujbean;
  }

  public void execute() {
    CmdServiceImpl commandSvc = new CmdServiceImpl();
    commandSvc._dataSvc = _dataSvc;
    commandSvc._networkSvc = _netSvc;
    commandSvc._lujbean = _lujbean;

    //TODO: event服务有待实现
    commandSvc._eventSvc = null;

    try {
      _cmdKit.getCommand().onExecute(new CmdContextImpl(
          _param, _loadResult, _cmdKit.getLogger(), commandSvc));

    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new UnsupportedOperationException(e);
    }
  }

  private final GameplayDataActor.CommandKit _cmdKit;
  private final Object _param;
  private final Object _loadResult;

  private final GameDataCommand.Data _dataSvc;
  private final GameDataCommand.Network _netSvc;

  private final BeanContext _lujbean;
}
