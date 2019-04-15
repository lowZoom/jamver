package luj.game.server.api.net;

public interface GameProtoHandler<P> {

  interface Context {

  }

  void onHandle(Context ctx);
}
