package luj.game.server.api.plugin;

/**
 * @see JamverDataLoadInit
 * @see JamverDataSaveInit
 */
public interface JamverDataRootInit {

  interface Context {

  }

  Object onInit(Context ctx);
}
