package luj.game.server.internal.data.types.map;

import static com.google.common.base.Preconditions.checkState;

import luj.game.server.internal.data.load.result.dirty.DirtyMarkable;

final class MapOp implements DirtyMarkable {

  @Override
  public void setDirtyMarker(Runnable marker) {
    checkState(_map._dirtyMarker == null);
    _map._dirtyMarker = marker;
  }

  @Override
  public void clearDirtyMarker() {
    _map._dirtyMarker = null;
  }

  DataMap<?, ?> _map;
}
