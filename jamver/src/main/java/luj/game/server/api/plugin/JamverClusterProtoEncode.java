package luj.game.server.api.plugin;

public interface JamverClusterProtoEncode {

  interface Context {

    Object getProtoObject();

    Return result();
  }

  interface Return {

    Return protoType(Class<?> val);

    Return protoData(byte[] val);
  }

  Return onEncode(Context ctx) throws Exception;
}
