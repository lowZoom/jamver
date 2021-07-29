package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.register;

import java.util.Map;
import luj.ava.spring.Internal;
import luj.game.server.internal.data.command.collect.CommandMapCollector;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

@Internal
final class OnRegisterCommand implements GameplayDataActor.Handler<RegisterCommandMsg> {

  @Override
  public void onHandle(Context ctx) {
    GameplayDataActor self = ctx.getActorState(this);
    RegisterCommandMsg msg = ctx.getMessage(this);

    Map<String, GameplayDataActor.CommandKit> addMap = new CommandMapCollector(
        msg.getDataCommand(), msg.getDataLoad()).collect();

    self.getCommandMap().putAll(addMap);
  }
}
