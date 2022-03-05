package luj.game.server.api;

import luj.game.server.internal.boot.node.ServerInstanceStarter;
import org.springframework.context.ApplicationContext;

public enum Jamver {
  ;

  public static void start(ApplicationContext appContext) {
    new ServerInstanceStarter(appContext).start();
  }
}
