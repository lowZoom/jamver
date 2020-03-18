package luj.game.server.api.plugin;

public interface JamverDataSaveInit {

  interface Context {

    <T> T getDataState();
  }

  Object onInit(Context ctx);
}
