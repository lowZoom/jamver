package luj.game.server.api.plugin;

public interface JamverNetReceivePacket<P> {

  interface Context {

    <P> P getPacket(JamverNetReceivePacket<P> plugin);

    Result finish();
  }

  interface Result {

    Result protoType(Class<?> type);

    Result protoObj(Object proto);
  }

  Result receive(Context ctx);
}
