package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache;

import luj.game.server.api.plugin.JamverDataRootInit;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.DataLoadPlugin;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSavePlugin;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @see luj.game.server.internal.inject.ServerBeanCollector#collect
 */
public class DataAllPlugin {

  public JamverDataRootInit getRootInitPlugin() {
    return _rootInitPlugin;
  }

  public DataLoadPlugin getLoadPlugin() {
    return _loadPlugin;
  }

  public DataSavePlugin getSavePlugin() {
    return _savePlugin;
  }

  @Autowired
  private JamverDataRootInit _rootInitPlugin;

  @Autowired
  private DataLoadPlugin _loadPlugin;

  @Autowired
  private DataSavePlugin _savePlugin;
}
