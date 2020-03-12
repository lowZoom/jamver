package luj.game.server.internal.data.load.meta;

public interface LoadResultMeta {

  Class<?> getCommandType();

  Object createResult();

  Object createRequest();
}
