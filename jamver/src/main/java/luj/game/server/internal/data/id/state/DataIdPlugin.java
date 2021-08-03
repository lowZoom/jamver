package luj.game.server.internal.data.id.state;

import luj.game.server.api.plugin.JamverDataIdGenNext;
import luj.game.server.api.plugin.JamverDataIdInit;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @see luj.game.server.internal.inject.ServerBeanCollector#collect
 */
public class DataIdPlugin {

  public JamverDataIdInit getInit() {
    return _init;
  }

  public JamverDataIdGenNext getGenerateNext() {
    return _generateNext;
  }

  @Autowired(required = false)
  private JamverDataIdInit _init;

  @Autowired(required = false)
  private JamverDataIdGenNext _generateNext;
}
