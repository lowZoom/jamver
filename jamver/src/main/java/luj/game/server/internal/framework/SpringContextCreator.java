package luj.game.server.internal.framework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;

public class SpringContextCreator {

  public AnnotationConfigApplicationContext create() {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

    ctx.getBeanFactory().registerSingleton(
        AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR, new BeanNameGen());

    return ctx;
  }
}
