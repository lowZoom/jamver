package luj.game.server.api.plugin;

/**
 * @see JamverDataLoadInit
 * @see JamverDataSaveInit
 */
public interface JamverDataRootInit {

  interface Context {

    /**
     * @see JamverBootRootInit#onInit
     */
    <T> T getStartParam();
  }

  Object onInit(Context ctx);
}
