package luj.game.server.internal.data.execute.group;

import luj.bean.api.BeanContext;
import luj.cluster.api.actor.Tellable;
import luj.config.api.container.ConfigContainer;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.internal.data.execute.DataCmdExecutor;
import luj.game.server.internal.data.load.result.LoadResultProxy;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

final class ElementImpl<C> implements GameDataCommandGroup.Element<C> {

  @SuppressWarnings("unchecked")
  @Override
  public Class<C> getCommandType() {
    return (Class<C>) _cmdKit.getCommandType();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends GameDataCommand<?, ?>> GameDataCommandGroup.Element<T> as(Class<T> command) {
    return (GameDataCommandGroup.Element<T>) this;
  }

  @Override
  public void execute() throws Exception {
    Object loadResult = _resultProxy.getInstance();

    new DataCmdExecutor(_cmdKit, _cmdParam, loadResult,
        _dataSvc, _eventRef, _configs, _lujbean).execute();
  }

  GameplayDataActor.CommandKit _cmdKit;
  Object _cmdParam;

  LoadResultProxy _resultProxy;
  GameDataCommand.Data _dataSvc;

  ConfigContainer _configs;
  Tellable _eventRef;

  ServerMessageHandler.Server _remoteRef;
  BeanContext _lujbean;
}
