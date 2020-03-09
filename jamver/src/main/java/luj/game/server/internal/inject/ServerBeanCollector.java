package luj.game.server.internal.inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ServerBeanCollector {

  public ServerBeanCollector(ApplicationContext appContext) {
    _appContext = appContext;
  }

  public ServerBeanRoot collect() {
    try (AnnotationConfigApplicationContext resultCtx = new AnnotationConfigApplicationContext()) {
      resultCtx.setParent(_appContext);

      resultCtx.register(ServerBeanRoot.class);
      resultCtx.refresh();

      return resultCtx.getBean(ServerBeanRoot.class);
    }
  }

  private final ApplicationContext _appContext;
}
