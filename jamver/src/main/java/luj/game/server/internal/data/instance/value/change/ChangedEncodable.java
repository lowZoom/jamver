package luj.game.server.internal.data.instance.value.change;

public interface ChangedEncodable {

  boolean isChanged();

  Object encodeChanged();
}
