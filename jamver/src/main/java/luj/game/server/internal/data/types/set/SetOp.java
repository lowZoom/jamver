package luj.game.server.internal.data.types.set;

import luj.game.server.internal.data.instance.value.ChangedApplicable;
import luj.game.server.internal.data.instance.value.ChangedEncodable;
import luj.game.server.internal.data.types.set.history.SetChangeApplier;
import luj.game.server.internal.data.types.set.history.SetChangeEncoder;
import luj.game.server.internal.data.types.set.history.SetChangedChecker;

final class SetOp implements ChangedEncodable, ChangedApplicable {

  @Override
  public boolean isChanged() {
    return SetChangedChecker.GET.isChanged(_set._value);
  }

  @Override
  public Object encodeChanged() {
    return SetChangeEncoder.GET.encode(_set._value);
  }

  @Override
  public void applyChanged() {
    SetChangeApplier.GET.apply(_set._value);
  }

  DataSet<?> _set;
}
