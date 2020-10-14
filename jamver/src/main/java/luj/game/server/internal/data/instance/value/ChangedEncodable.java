package luj.game.server.internal.data.instance.value;

public interface ChangedEncodable {

  boolean isChanged();

  Object encodeChanged();
}
