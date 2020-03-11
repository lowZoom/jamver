package luj.game.server.internal.data.execute;

import luj.cluster.api.actor.ActorMessageHandler;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.GameplayDataActor;

public class DataCmdExecutor {

  public DataCmdExecutor(GameplayDataActor.CommandKit cmdKit, Object param, Object loadResult,
      ActorMessageHandler.Ref dataRef) {
    _cmdKit = cmdKit;
    _param = param;
    _loadResult = loadResult;
    _dataRef = dataRef;
  }

  public void execute() {
    DataServiceImpl dataSvc = new DataServiceImpl(_dataRef);
    CommandServiceImpl commandSvc = new CommandServiceImpl(dataSvc);

    try {
      _cmdKit.getCommand().onExecute(new CommandContextImpl(
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
  private final ActorMessageHandler.Ref _dataRef;
}
