package luj.game.server.internal.luj.lujcluster.actor.start.trigger;

import java.util.List;
import luj.game.server.api.boot.GameStartListener;

public class TriggerStartMsg {

  public TriggerStartMsg(List<GameStartListener> starterList) {
    _starterList = starterList;
  }

  public List<GameStartListener> getStarterList() {
    return _starterList;
  }

  private final List<GameStartListener> _starterList;
}
