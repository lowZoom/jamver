package luj.game.server.internal.data.types.map;

import luj.game.server.internal.data.instance.value.ChangedApplicable;
import luj.game.server.internal.data.instance.value.ChangedEncodable;
import luj.game.server.internal.data.types.map.history.MapChangeEncoder;
import luj.game.server.internal.data.types.map.history.MapChangedChecker;
import luj.game.server.internal.data.types.map.history.MapModificationApplier;

final class MapOp implements ChangedEncodable, ChangedApplicable {

  @Override
  public boolean isChanged() {
    return MapChangedChecker.GET.isChanged(_map._data);
  }

  @Override
  public Object encodeChanged() {
    return MapChangeEncoder.GET.encode(_map._data);
  }

  @Override
  public void applyChanged() {
    MapModificationApplier.GET.apply(_map._data);
  }

  DataMap<?, ?> _map;
}
