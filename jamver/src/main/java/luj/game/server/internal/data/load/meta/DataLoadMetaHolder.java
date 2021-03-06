package luj.game.server.internal.data.load.meta;

import luj.game.server.internal.data.load.meta.generate.DataLoadRequestFactory;
import luj.game.server.internal.data.load.meta.generate.DataLoadResultFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DataLoadMetaHolder<T> {

  public DataLoadResultFactory<T> getResultFactory() {
    return _resultFactory;
  }

  public DataLoadRequestFactory<T> getRequestFactory() {
    return _requestFactory;
  }

  @Autowired
  private DataLoadResultFactory<T> _resultFactory;

  @Autowired
  private DataLoadRequestFactory<T> _requestFactory;
}
