package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save;

import luj.game.server.api.plugin.JamverDataSaveInit;
import luj.game.server.api.plugin.JamverDataSaveIo;
import luj.game.server.internal.inject.ServerBeanCollector;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @see ServerBeanCollector#collect
 */
public class DataSavePlugin {

  public JamverDataSaveInit getSaveInit() {
    return _saveInit;
  }

  public JamverDataSaveIo<?> getSaveIo() {
    return _saveIo;
  }

  @Autowired
  private JamverDataSaveInit _saveInit;

  @Autowired
  private JamverDataSaveIo<?> _saveIo;
}
