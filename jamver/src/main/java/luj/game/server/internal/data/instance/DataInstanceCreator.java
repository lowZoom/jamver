package luj.game.server.internal.data.instance;

import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.ImmutableMap;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataInstanceCreator {

  public DataInstanceCreator(Class<?> dataType) {
    _dataType = dataType;
  }

  @SuppressWarnings("unchecked")
  public <T> T create() {
    checkState(_dataType.isInterface(), _dataType);

    Map<String, Object> listMap = Arrays.stream(_dataType.getMethods())
        .filter(m -> m.getReturnType() == List.class)
        .collect(Collectors.toMap(Method::getName, m -> new ArrayList<>()));

    return (T) new InstanceTempProxy(_dataType, new HashMap<>(ImmutableMap.<String, Object>builder()
        .putAll(listMap)
        .build())).newInstance();
  }

  private final Class<?> _dataType;
}
