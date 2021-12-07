package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.finish;

import java.util.List;
import luj.game.server.internal.data.instancev2.DataEntity;

public class CmdExecFinishMsg {

  public CmdExecFinishMsg(List<DataEntity> createLog, List<DataEntity> loadLog) {
    _createLog = createLog;
    _loadLog = loadLog;
  }

  public List<DataEntity> getCreateLog() {
    return _createLog;
  }

  public List<DataEntity> getLoadLog() {
    return _loadLog;
  }

  private final List<DataEntity> _createLog;

  private final List<DataEntity> _loadLog;
}
