package luj.game.server.api.plugin;

public interface JamverClusterProtoDecode {

  interface Context {

    Class<?> getProtoType();

    byte[] getProtoData();
  }

  Object onDecode(Context ctx) throws Exception;
}
