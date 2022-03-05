package luj.game.server.internal.boot.node;


import luj.ava.spring.Internal;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "luj.game.server.internal",
    includeFilters = @ComponentScan.Filter(Internal.class))
final class InternalInjectConf {
  // NOOP
}
