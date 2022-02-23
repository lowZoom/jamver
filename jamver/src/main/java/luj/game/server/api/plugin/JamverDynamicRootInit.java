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

    void registerDataCommand(Collection<GameDataCommand<?, ?>> command,
        Collection<GameDataLoad<?, ?>> load);

    void registerServerJoinListener(Collection<ServerJoinListener> listener);

    void registerServerMessageHandler(Collection<ServerMessageHandler<?>> handler);
  }

  void onInit(Context ctx);
}
