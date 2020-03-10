package luj.game.server.internal.data.execute.load;

import luj.game.server.api.data.GameDataLoad;

public class DataLoadRequestMaker {

  public DataLoadRequestMaker(GameDataLoad<?, ?> loader, Object param) {
    _loader = loader;
    _param = param;
  }

  public void make() {
    _loader.onLoad(new LoadContextImpl(_param));
  }

  private final GameDataLoad<?, ?> _loader;

  private final Object _param;
}
