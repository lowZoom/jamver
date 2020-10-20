package luj.game.server.internal.data.instance.value.change;

public interface ChangedApplicable {

  boolean isChanged();

  void applyChanged();
}
