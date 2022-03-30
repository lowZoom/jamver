package luj.game.server.internal.config.reload;

import luj.config.api.container.ConfigContainer;
import luj.game.server.api.plugin.JamverConfigReload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReloadInvoker {

  public ConfigReloadInvoker(JamverConfigReload reloadPlugin, Object startParam) {
    _reloadPlugin = reloadPlugin;
    _startParam = startParam;
  }

  public ConfigContainer invoke() {
    if (_reloadPlugin == null) {
      LOG.debug("[game]未启用配置模块");
      return null;
    }

    ReloadContextImpl ctx = new ReloadContextImpl();
    ctx._startParam = _startParam;

    return _reloadPlugin.onReload(ctx);
  }

  private static final Logger LOG = LoggerFactory.getLogger(ConfigReloadInvoker.class);

  private final JamverConfigReload _reloadPlugin;
  private final Object _startParam;
}
