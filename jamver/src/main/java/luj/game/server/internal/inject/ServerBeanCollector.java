package luj.game.server.internal.inject;

import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.load.DataLoadPlugin;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.DataSavePlugin;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ServerBeanCollector {

  public ServerBeanCollector(ApplicationContext externalCtx) {
    _externalCtx = externalCtx;
  }

  public ServerBeanRoot collect() {
    try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext()) {
      ctx.setParent(_externalCtx);

      ctx.register(DataLoadPlugin.class);
      ctx.register(DataSavePlugin.class);

      ctx.register(ServerBeanRoot.class);
      ctx.refresh();

      return ctx.getBean(ServerBeanRoot.class);
    }
  }

  private final ApplicationContext _externalCtx;
}
