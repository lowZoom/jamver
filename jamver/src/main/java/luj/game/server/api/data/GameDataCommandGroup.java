package luj.game.server.api.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import org.springframework.stereotype.Component;

//TODO: 考虑废除此机制
@Deprecated
public interface GameDataCommandGroup {

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Component
  @interface Register {
    // NOOP
  }

  interface Context {

    List<Element<?>> elements();

    <P> P getParam(Element<? extends GameDataCommand<P, ?>> elem);

    <D> D getData(Element<? extends GameDataCommand<?, D>> elem);
  }

  interface Element<C> {

    Class<C> getCommandType();

    <T extends GameDataCommand<?, ?>> Element<T> as(Class<T> command);

    void execute() throws Exception;
  }

  void onExecute(Context ctx) throws Exception;
}
