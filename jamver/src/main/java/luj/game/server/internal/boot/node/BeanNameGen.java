package luj.game.server.internal.boot.node;

import java.lang.reflect.Modifier;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

public class BeanNameGen extends AnnotationBeanNameGenerator {

  @Override
  protected String buildDefaultBeanName(BeanDefinition definition) {
    return loadClass(definition.getBeanClassName())
        .flatMap(this::generateImpl)
        .orElseGet(() -> super.buildDefaultBeanName(definition));
  }

  private Optional<Class<?>> loadClass(String className) {
    try {
      return Optional.of(Class.forName(className));

    } catch (ClassNotFoundException e) {
      LOG.error(className, e);
      return Optional.empty();
    }
  }

  private Optional<String> generateImpl(Class<?> beanType) {
    if ((beanType.getModifiers() & Modifier.PUBLIC) == 0) {
      // 非public类使用类全名
      return Optional.of(beanType.getName());
    }
    return Optional.empty();
  }

  private static final Logger LOG = LoggerFactory.getLogger(BeanNameGen.class);
}
