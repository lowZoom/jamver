package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.schedule;

import java.time.Duration;

public class DatacmdScheduleMsg {

  public DatacmdScheduleMsg(String cmdType, Object param, Comparable<?> scheduleId,
      Duration delay) {
    _cmdType = cmdType;
    _param = param;
    _scheduleId = scheduleId;
    _delay = delay;
  }

  public String getCmdType() {
    return _cmdType;
  }

  public Object getParam() {
    return _param;
  }

  public Comparable<?> getScheduleId() {
    return _scheduleId;
  }

  public Duration getDelay() {
    return _delay;
  }

  private final String _cmdType;
  private final Object _param;

  private final Comparable<?> _scheduleId;
  private final Duration _delay;
}
