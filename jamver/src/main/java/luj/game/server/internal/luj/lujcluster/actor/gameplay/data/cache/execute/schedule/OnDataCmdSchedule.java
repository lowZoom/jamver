package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.schedule;

import akka.actor.Cancellable;
import luj.ava.spring.Internal;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.prepare.DatacmdExecMsg;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.schedule.state.ScheduleMap;

@Internal
final class OnDataCmdSchedule implements GameplayDataActor.Handler<DatacmdScheduleMsg> {

  @Override
  public void onHandle(Context ctx) {
    GameplayDataActor self = ctx.getActorState(this);
    DatacmdScheduleMsg msg = ctx.getMessage(this);

    ScheduleMap schedMap = self.getScheduleMap();
    ScheduleMap.TypeMap typeSched = schedMap.get(msg.getCmdType());

    Ref selfRef = ctx.getActorRef();
    DatacmdExecMsg execMsg = new DatacmdExecMsg(msg.getCmdType(), msg.getParam(), null);
    Cancellable newSched = selfRef.schedule(msg.getDelay(), execMsg);

    Cancellable oldSched = typeSched.put(msg.getScheduleId(), newSched);
    if (oldSched != null) {
      oldSched.cancel();
    }
  }
}
