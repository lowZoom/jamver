package luj.game.server.internal.data.command.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.service.CommandService;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class CommandServiceFactory {

  public CommandServiceFactory(BeanContext lujbean, Tellable dataRef,
      ServerMessageHandler.Server remoteRef, Class<?> commandType,
      Map<String, GameplayDataActor.CommandKit> commandMap) {
    _lujbean = lujbean;
    _dataRef = dataRef;
    _remoteRef = remoteRef;
    _commandType = commandType;
    _commandMap = commandMap;
  }

  public <P> CommandService<P> create() {
    var svc = new CommandServiceImpl<P>();
    svc._commandType = _commandType;

    GameplayDataActor.CommandKit cmdKit = _commandMap.get(_commandType.getName());
    checkNotNull(cmdKit, _commandType.getName());
    svc._paramType = cmdKit.getParamType();

    svc._factory = this;
    return svc;
  }

  final BeanContext _lujbean;

  final Tellable _dataRef;
  final ServerMessageHandler.Server _remoteRef;

  private final Class<?> _commandType;
  private final Map<String, GameplayDataActor.CommandKit> _commandMap;
}
