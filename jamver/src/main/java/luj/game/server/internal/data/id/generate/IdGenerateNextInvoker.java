package luj.game.server.internal.data.id.generate;

import luj.game.server.api.plugin.JamverDataIdGenNext;
import luj.game.server.internal.data.id.state.DataIdGenState;

public enum IdGenerateNextInvoker {
  GET;

  public Comparable<?> invoke(DataIdGenState idGenState) {
    JamverDataIdGenNext<?> generatePlugin = idGenState.getGeneratePlugin();

    ContextImpl ctx = new ContextImpl();
    ctx._appState = idGenState.getAppState();

    return generatePlugin.generate(ctx);
  }
}
