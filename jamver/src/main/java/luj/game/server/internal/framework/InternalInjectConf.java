package luj.game.server.internal.framework;


import luj.ava.spring.Internal;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "luj.game.server.internal",
    includeFilters = @ComponentScan.Filter(Internal.class))
final class InternalInjectConf {
  // NOOP
}
