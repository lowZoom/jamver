package luj.game.server.api.plugin;

import java.util.Collection;

public interface JamverDynamicRootInit {

  interface Context {

    /**
     * @see JamverBootRootInit.Return#param
     */
    <T> T getStartParam();

    void registerAll(Collection<?> beans);
  }

  void onInit(Context ctx);
}
