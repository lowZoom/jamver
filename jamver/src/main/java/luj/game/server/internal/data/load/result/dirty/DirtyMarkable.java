package luj.game.server.internal.data.load.result.dirty;

public interface DirtyMarkable {

  void setDirtyMarker(Runnable marker);

  //TODO: 还数据的时候清空
  void clearDirtyMarker();
}
