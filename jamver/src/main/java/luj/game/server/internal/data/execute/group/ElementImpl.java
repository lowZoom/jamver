package luj.game.server.internal.data.execute.group;

import luj.bean.api.BeanContext;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.internal.data.execute.DataCmdExecutor;
import luj.game.server.internal.data.load.result.LoadResultProxy;
import luj.game.server.internal.data.execute.service.network.NetServiceFactory;
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
  public void execute() {
    Object loadResult = _resultProxy.getInstance();
    GameDataCommand.Network netSvc = new NetServiceFactory(_remoteRef).create();

    new DataCmdExecutor(_cmdKit, _cmdParam, loadResult, _dataSvc, netSvc, _lujbean).execute();
  }

  GameplayDataActor.CommandKit _cmdKit;
  Object _cmdParam;

  LoadResultProxy _resultProxy;
  GameDataCommand.Data _dataSvc;

  ServerMessageHandler.Server _remoteRef;
  BeanContext _lujbean;
}
