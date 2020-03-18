package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load;

import luj.game.server.api.plugin.JamverDataLoadInit;
import luj.game.server.api.plugin.JamverDataLoadLoad;
import luj.game.server.internal.inject.ServerBeanCollector;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @see ServerBeanCollector#collect
 */
public class DataLoadPlugin {

  public JamverDataLoadInit getLoadInit() {
    return _loadInit;
  }

  public JamverDataLoadLoad getDataLoader() {
    return _dataLoader;
  }

  @Autowired
  private JamverDataLoadInit _loadInit;

  @Autowired
  private JamverDataLoadLoad _dataLoader;
}
