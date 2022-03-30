package luj.game.server.api.plugin;

import luj.config.api.container.ConfigContainer;

public interface JamverConfigReload {

  interface Context {

    <T> T getStartParam();
  }

  ConfigContainer onReload(Context ctx);
}
