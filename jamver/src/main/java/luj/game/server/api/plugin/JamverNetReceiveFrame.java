package luj.game.server.api.plugin;

import io.netty.buffer.ByteBuf;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Primary;

public interface JamverNetReceiveFrame {

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Primary
  @interface Head {
    // NOOP
  }

  interface Context {

    ByteBuf getLastFrame();

    <T> T getConnectionState();

    Result then();
  }

  interface Result {

    Result waitBytes(int byteCount);

    Result nextReceiver(JamverNetReceiveFrame receiver);

    Result completeRecv(Object packet);
  }

  Result receive(Context ctx) throws Exception;
}
