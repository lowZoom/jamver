package luj.game.server.internal.boot.node;


import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
    "luj.game.server.internal"
}, includeFilters = {
    @ComponentScan.Filter(luj.ava.spring.Internal.class),
    @ComponentScan.Filter(luj.spring.anno.Internal.class),
})
final class InternalInjectConf {
  // NOOP
}
