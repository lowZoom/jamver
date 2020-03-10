package luj.game.server.internal.data.execute;

import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.GameplayDataActor;

public class DataCmdExecutor {

  public DataCmdExecutor(GameplayDataActor.CommandKit cmdKit, Object param, Object loadResult) {
    _cmdKit = cmdKit;
    _param = param;
    _loadResult = loadResult;
  }

  public void execute() {
    DataServiceImpl dataSvc = new DataServiceImpl();
    CommandServiceImpl commandSvc = new CommandServiceImpl(dataSvc);

    try {
      _cmdKit.getCommand().onExecute(new CommandContextImpl(_param, _loadResult, commandSvc));
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new UnsupportedOperationException(e);
    }
  }

  private final GameplayDataActor.CommandKit _cmdKit;

  private final Object _param;
  private final Object _loadResult;
}
