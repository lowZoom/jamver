package luj.game.server.api.plugin;

public interface JamverDataIdGenNext<T> {

  interface Context {

    /**
     * @see JamverDataIdInit#onInit
     */
    <T> T getGenerateState(JamverDataIdGenNext<T> plugin);
  }

  Comparable<?> generate(Context ctx);
}
