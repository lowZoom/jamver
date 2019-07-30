package luj.game.server.internal.data.load;

import luj.game.server.internal.data.load.generate.DataLoadRequestFactory;
import luj.game.server.internal.data.load.generate.DataLoadResultFactory;

final class LoadResultMetaImpl implements LoadResultMeta {

  LoadResultMetaImpl(Class<?> commandType, DataLoadResultFactory<?> resultFactory,
      DataLoadRequestFactory<?> requestFactory) {
    _commandType = commandType;
    _resultFactory = resultFactory;
    _requestFactory = requestFactory;
  }

  @Override
  public Class<?> getCommandType() {
    return _commandType;
  }

  @Override
  public Object createResult() {
    return _resultFactory.create();
  }

  @Override
  public Object createRequest() {
    return _requestFactory.create();
  }

  private final Class<?> _commandType;

  private final DataLoadResultFactory<?> _resultFactory;
  private final DataLoadRequestFactory<?> _requestFactory;
}
