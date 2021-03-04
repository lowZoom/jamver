package luj.game.server.api.plugin;


/**
 * @see JamverDataLoadInit
 * @see JamverDataSaveInit
 */
public interface JamverDataRootInit {

  interface Context {

    /**
     * @see luj.game.server.api.plugin.JamverBootRootInit.Return#param
     */
    <T> T getStartParam();
  }

  Object onInit(Context ctx);
}
