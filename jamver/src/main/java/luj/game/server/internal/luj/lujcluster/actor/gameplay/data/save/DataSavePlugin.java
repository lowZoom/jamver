package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save;

import luj.game.server.api.plugin.JamverDataSaveInit;
import luj.game.server.api.plugin.JamverDataSaveIo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @see luj.game.server.internal.inject.ServerBeanCollector#collect
 */
public class DataSavePlugin {

  public JamverDataSaveInit getSaveInit() {
    return _saveInit;
  }

  public JamverDataSaveIo<?> getSaveIo() {
    return _saveIo;
  }

  @Autowired(required = false)
  private JamverDataSaveInit _saveInit;

  @Autowired(required = false)
  private JamverDataSaveIo<?> _saveIo;
}
