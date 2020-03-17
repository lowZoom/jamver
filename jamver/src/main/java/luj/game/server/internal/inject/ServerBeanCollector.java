package luj.game.server.internal.inject;

import luj.game.server.internal.luj.lujcluster.actor.gameplay.dataload.DataLoadPlugin;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ServerBeanCollector {

  public ServerBeanCollector(ApplicationContext externalCtx) {
    _externalCtx = externalCtx;
  }

  public ServerBeanRoot collect() {
    try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext()) {
      ctx.setParent(_externalCtx);

      ctx.register(ServerBeanRoot.class);
      ctx.register(DataLoadPlugin.class);
      ctx.refresh();

      return ctx.getBean(ServerBeanRoot.class);
    }
  }

  private final ApplicationContext _externalCtx;
}
