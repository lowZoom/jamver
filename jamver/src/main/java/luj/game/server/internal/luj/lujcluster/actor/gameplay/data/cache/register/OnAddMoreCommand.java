package luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.register;

import java.util.Map;
import luj.ava.spring.Internal;
import luj.game.server.internal.data.command.collect.CommandMapCollector;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnAddMoreCommand implements GameplayDataActor.Handler<AddMoreCommandMsg> {

  @Override
  public void onHandle(Context ctx) {
    GameplayDataActor self = ctx.getActorState(this);
    AddMoreCommandMsg msg = ctx.getMessage(this);

    Map<String, GameplayDataActor.CommandKit> addMap = new CommandMapCollector(
        msg.getDataCommand(), msg.getDataLoad()).collect();

    LOG.debug("[game]新增DataCommand，数量：{}", addMap.size());
    self.getCommandMap().putAll(addMap);
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnAddMoreCommand.class);
}
