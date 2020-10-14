package luj.game.server.internal.data.instance.value;

public interface ChangedApplicable {

  boolean isChanged();

  void applyChanged();
}
