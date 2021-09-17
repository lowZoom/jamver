package luj.game.server.internal.data.execute.load.result.field;

import java.util.Collection;
import luj.ava.reflect.type.TypeX;
import luj.game.server.internal.data.execute.load.result.ResultFieldProxy;

final class ResultFieldImpl implements ResultFieldProxy.Field {

  @Override
  public String getName() {
    return _name;
  }

  @Override
  public Class<?> getDataType() {
    return _dataType;
  }

  String _name;

  Class<?> _dataType;
}
