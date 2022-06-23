package luj.game.server.api.plugin;

import java.util.Collection;
import luj.game.server.api.cluster.ServerJoinListener;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataLoad;

public interface JamverDynamicRootInit {

  interface Context {

    /**
     * @see JamverBootRootInit.Return#param
     */
    <T> T getStartParam();

    /**
     * @see #registerAll
     */
    @Deprecated
    void registerDataCommand(Collection<GameDataCommand<?, ?>> command,
        Collection<GameDataLoad<?, ?>> load);

    /**
     * @see #registerAll
     */
    @Deprecated
    void registerServerJoinListener(Collection<ServerJoinListener> listener);

    /**
     * @see #registerAll
     */
    @Deprecated
    void registerServerMessageHandler(Collection<ServerMessageHandler<?>> handler);

    void registerAll(Collection<?> beans);
  }

  void onInit(Context ctx);
}
