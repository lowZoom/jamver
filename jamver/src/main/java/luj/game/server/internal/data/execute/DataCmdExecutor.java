package luj.game.server.internal.data.execute;

import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class DataCmdExecutor {

  public DataCmdExecutor(GameplayDataActor.CommandKit cmdKit, Object param, Object loadResult,
      DataServiceImpl dataSvc) {
    _cmdKit = cmdKit;
    _param = param;
    _loadResult = loadResult;
    _dataSvc = dataSvc;
  }

  public void execute() {
    CmdServiceImpl commandSvc = new CmdServiceImpl(_dataSvc);

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
  private final DataServiceImpl _dataSvc;
}
