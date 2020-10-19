package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save;

import luj.game.server.api.plugin.JamverDataSaveCreate;
import luj.game.server.api.plugin.JamverDataSaveInit;
import luj.game.server.api.plugin.JamverDataSaveIo;
import luj.game.server.api.plugin.JamverDataSaveUpdate;
import luj.game.server.internal.inject.ServerBeanCollector;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @see ServerBeanCollector#collect
 */
public class DataSavePlugin {

  public JamverDataSaveInit getSaveInit() {
    return _saveInit;
  }

  public JamverDataSaveCreate getSaveCreate() {
    return _saveCreate;
  }

  public JamverDataSaveUpdate getSaveUpdate() {
    return _saveUpdate;
  }

  public JamverDataSaveIo<?> getSaveIo() {
    return _saveIo;
  }

  @Autowired
  private JamverDataSaveInit _saveInit;

  @Autowired
  private JamverDataSaveCreate _saveCreate;

  @Autowired
  private JamverDataSaveUpdate _saveUpdate;

  @Autowired
  private JamverDataSaveIo<?> _saveIo;
}
