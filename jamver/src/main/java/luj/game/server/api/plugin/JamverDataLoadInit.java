package luj.game.server.api.plugin;

public interface JamverDataLoadInit {

  interface Context {

    <T> T getDataState();
  }

  Object onInit(Context ctx);
}
