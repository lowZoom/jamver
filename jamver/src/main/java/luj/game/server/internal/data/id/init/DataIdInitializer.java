package luj.game.server.internal.data.id.init;

import luj.game.server.api.plugin.JamverDataIdInit;
import luj.game.server.internal.data.id.state.DataIdGenState;
import luj.game.server.internal.data.id.state.DataIdPlugin;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class DataIdInitializer {

  public DataIdInitializer(GameplayDataActor self) {
    _self = self;
  }

  public void init() {
    DataIdPlugin idAllPlugin = _self.getAllPlugin().getIdPlugin();
    JamverDataIdInit initPlugin = idAllPlugin.getInit();

    InitContextImpl ctx = new InitContextImpl();
    ctx._return = new InitReturnImpl();

    InitReturnImpl result = (InitReturnImpl) initPlugin.onInit(ctx);
    DataIdGenState idState = _self.getIdGenState();

    idState.setAppState(result._state);
    idState.setIdField(result._idField);
  }

  private final GameplayDataActor _self;
}
