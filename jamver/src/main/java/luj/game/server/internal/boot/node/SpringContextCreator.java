package luj.game.server.internal.boot.node;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContextCreator {

  public AnnotationConfigApplicationContext create() {
    var ctx = new AnnotationConfigApplicationContext();
    ctx.setBeanNameGenerator(new BeanNameGen());
    return ctx;
  }
}
