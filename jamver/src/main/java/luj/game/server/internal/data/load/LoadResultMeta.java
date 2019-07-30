package luj.game.server.internal.data.load;

public interface LoadResultMeta {

  Class<?> getCommandType();

  Object createResult();

  Object createRequest();
}
