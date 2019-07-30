package luj.game.server.internal.data.load;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import luj.ava.reflect.type.TypeX;
import luj.ava.stream.StreamX;

public class LoadResultMetaCollector {

  public LoadResultMetaCollector(List<DataLoadMetaHolder<?>> metaList) {
    _metaList = metaList;
  }

  public Map<Class<?>, LoadResultMeta> collect() {
    return StreamX.from(_metaList)
        .map(this::toMeta)
        .collect(toMap(LoadResultMeta::getCommandType, Function.identity()));
  }

  private LoadResultMeta toMeta(DataLoadMetaHolder<?> holder) {
    Class<?> cmdType = getCommandType(holder);
    return new LoadResultMetaImpl(cmdType, holder.getResultFactory(), holder.getRequestFactory());
  }

  private Class<?> getCommandType(DataLoadMetaHolder<?> holder) {
    return TypeX.ofInstance(holder)
        .getTypeParam(0)
        .asClass();
  }

  private final List<DataLoadMetaHolder<?>> _metaList;
}
