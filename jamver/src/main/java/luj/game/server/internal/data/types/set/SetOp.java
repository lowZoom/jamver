package luj.game.server.internal.data.types.set;

import luj.game.server.internal.data.instance.value.change.ChangedApplicable;
import luj.game.server.internal.data.instance.value.change.ChangedEncodable;
import luj.game.server.internal.data.instance.value.create.CreatedEncodable;
import luj.game.server.internal.data.types.set.history.SetChangeApplier;
import luj.game.server.internal.data.types.set.history.SetChangeEncoder;
import luj.game.server.internal.data.types.set.history.SetChangedChecker;
import luj.game.server.internal.data.types.set.history.SetInitEncoder;

final class SetOp implements ChangedEncodable, ChangedApplicable, CreatedEncodable {

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

  @Override
  public Object encodeInit() {
    return SetInitEncoder.GET.encode(_set._value);
  }

  DataSet<?> _set;
}
