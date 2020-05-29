package luj.game.server.internal.framework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContextCreator {

  public AnnotationConfigApplicationContext create() {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
    ctx.setBeanNameGenerator(new BeanNameGen());
    return ctx;
  }
}
