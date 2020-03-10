package luj.game.server.internal.luj.lujcache;

import luj.ava.spring.Internal;
import luj.cache.api.request.RequestWalkListener;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Internal
final class OnWalkReq implements RequestWalkListener {

  @Override
  public void onWalk(Context ctx) {
    throw new NotImplementedException();
  }
}
