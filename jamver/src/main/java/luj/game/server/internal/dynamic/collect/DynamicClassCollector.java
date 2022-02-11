package luj.game.server.internal.dynamic.collect;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Consumer;
import luj.game.server.internal.inject.DataCommandCollect;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

public class DynamicClassCollector {

  public DynamicClassCollector(Collection<Class<?>> classList,
      Consumer<GenericApplicationContext> moreBeanRegister) {
    _classList = classList;
    _moreBeanRegister = moreBeanRegister;
  }

  public <T> T collect(Class<T> injectRoot) {
    try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext()) {
      // 避免OrderUtils缓存
      ctx.getDefaultListableBeanFactory().setDependencyComparator(null);

      _classList.stream()
          .map(AnnotatedGenericBeanDefinition::new)
          .filter(this::isCandidateComponent)
          .forEach(d -> ctx.register(d.getBeanClass()));

      //TODO: 看看有无必要搞个自动扫描并注册 根对象所有字段
      ctx.register(DataCommandCollect.class);

      if (_moreBeanRegister != null) {
        _moreBeanRegister.accept(ctx);
      }

      ctx.register(injectRoot);
      ctx.refresh();

      return ctx.getBean(injectRoot);
    }
  }

  private boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
    AnnotationMetadata metadata = beanDefinition.getMetadata();
    MetaReaderImpl reader = new MetaReaderImpl();
    reader._metadata = metadata;

    try {
      if (!COMPONENT.match(reader, reader)) {
        return false;
      }
    } catch (IOException e) {
      throw new UnsupportedOperationException(e);
    }

    return metadata.isIndependent() && (metadata.isConcrete() ||
        (metadata.isAbstract() && metadata.hasAnnotatedMethods(Lookup.class.getName())));
  }

  private static final AnnotationTypeFilter COMPONENT = new AnnotationTypeFilter(Component.class);

  private final Collection<Class<?>> _classList;
  private final Consumer<GenericApplicationContext> _moreBeanRegister;
}
