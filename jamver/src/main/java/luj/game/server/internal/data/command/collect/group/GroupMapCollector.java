package luj.game.server.internal.data.command.collect.group;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import luj.ava.stream.StreamX;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class GroupMapCollector {

  public GroupMapCollector(List<GameDataCommandGroup> groupList) {
    _groupList = groupList;
  }

  public Map<Class<?>, GameplayDataActor.GroupKit> collect() {
    return StreamX.from(_groupList)
        .map(this::makeKit)
        .collect(toMap(KitImpl::getGroupType, Function.identity()));
  }

  private KitImpl makeKit(GameDataCommandGroup group) {
    KitImpl kit = new KitImpl();
    kit._group = group;
    kit._groupType = group.getClass();
    return kit;
  }

  private final List<GameDataCommandGroup> _groupList;
}
