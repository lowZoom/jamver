package luj.game.server.api.plugin;

public interface JamverDataIdInit {

  interface Context {

    <T> T getDataState();

    Return finishWith();
  }

  interface Return {

    /**
     * @param state 必须支持线程安全
     */
    Return state(Object state);

    Return idField(String field);

    Return globalId(Comparable<?> id);
  }

  Return onInit(Context ctx);
}
