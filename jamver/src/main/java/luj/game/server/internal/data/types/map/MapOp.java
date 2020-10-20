package luj.game.server.internal.data.types.map;

import luj.game.server.internal.data.instance.value.change.ChangedApplicable;
import luj.game.server.internal.data.instance.value.change.ChangedEncodable;
import luj.game.server.internal.data.instance.value.create.CreatedEncodable;
import luj.game.server.internal.data.types.map.history.MapChangeEncoder;
import luj.game.server.internal.data.types.map.history.MapChangedChecker;
import luj.game.server.internal.data.types.map.history.MapInitEncoder;
import luj.game.server.internal.data.types.map.history.MapModificationApplier;

final class MapOp implements ChangedEncodable, ChangedApplicable, CreatedEncodable {

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

  @Override
  public Object encodeInit() {
    return MapInitEncoder.GET.encode(_map._data);
  }

  DataMap<?, ?> _map;
}
