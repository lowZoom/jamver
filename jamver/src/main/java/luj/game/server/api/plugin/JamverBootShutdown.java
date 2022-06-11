package luj.game.server.api.plugin;

public interface JamverBootShutdown {

  interface Context {

    <T> T getShutdownParam();
  }

  void onShutdown(Context ctx) throws Exception;
}
