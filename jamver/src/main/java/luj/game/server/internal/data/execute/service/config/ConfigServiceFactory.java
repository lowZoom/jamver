package luj.game.server.internal.data.execute.service.config;

import luj.config.api.container.ConfigContainer;
import luj.game.server.api.data.GameDataCommand;

public enum ConfigServiceFactory {
  GET;

  public <C> GameDataCommand.Config<C> create(Class<C> configType, String typeKey,
      ConfigContainer configs) {
    ConfigServiceImpl<C> svc = new ConfigServiceImpl<>();

    svc._configType = configType;
    svc._typeMap = configs.findType(typeKey);

    return svc;
  }
}
