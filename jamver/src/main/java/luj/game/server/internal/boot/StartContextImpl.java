package luj.game.server.internal.boot;

import luj.game.server.api.boot.GameStartListener;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

final class StartContextImpl implements GameStartListener.Context {

  @Override
  public GameStartListener.Service service() {
    throw new NotImplementedException();
  }
}
