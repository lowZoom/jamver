package luj.game.server.internal.data.execute.load.missing.log;

import java.util.Map;
import luj.game.server.internal.data.execute.load.missing.DataReadyChecker;

public class MissingLog {

  public MissingLog(Map<String, DataReadyChecker.Missing> logMap) {
    _logMap = logMap;
  }

  public Map<String, DataReadyChecker.Missing> getLogMap() {
    return _logMap;
  }

  private final Map<String, DataReadyChecker.Missing> _logMap;
}
