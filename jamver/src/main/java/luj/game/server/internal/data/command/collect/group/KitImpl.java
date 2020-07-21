package luj.game.server.internal.data.command.collect.group;

import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

final class KitImpl implements GameplayDataActor.GroupKit {

  @Override
  public GameDataCommandGroup getGroup() {
    return _group;
  }

  @Override
  public Class<?> getGroupType() {
    return _groupType;
  }

  GameDataCommandGroup _group;

  Class<?> _groupType;
}
