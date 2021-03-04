package luj.game.server.api.plugin;

public interface JamverNetReceivePacket<P> {

  interface Context {

    <P> P getPacket(JamverNetReceivePacket<P> plugin);
  }

  void onReceive(Context ctx);
}
