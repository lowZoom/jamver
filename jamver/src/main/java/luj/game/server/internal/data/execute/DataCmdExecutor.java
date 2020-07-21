package luj.game.server.internal.data.execute;

import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class DataCmdExecutor {

  public DataCmdExecutor(GameplayDataActor.CommandKit cmdKit, Object param, Object loadResult,
      GameDataCommand.Data dataSvc, GameDataCommand.Network netSvc) {
    _cmdKit = cmdKit;
    _param = param;
    _loadResult = loadResult;
    _dataSvc = dataSvc;
    _netSvc = netSvc;
  }

  public void execute() {
    //TODO: event服务有待实现
    CmdServiceImpl commandSvc = new CmdServiceImpl(_dataSvc, null, _netSvc);
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
}
