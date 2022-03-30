package luj.game.server.internal.data.execute.service.config;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.stream.Stream;
import luj.config.api.container.ConfigItem;
import luj.config.api.container.TypeMap;
import luj.game.server.api.data.GameDataCommand;

final class ConfigServiceImpl<C> implements GameDataCommand.Config<C> {

  @Override
  public C find(Comparable<?> id) {
    ConfigItem item = _typeMap.findItem(id);
    return (item == null) ? null : item.getValue(_configType);
  }

  @Override
  public C findGlobal() {
    ConfigItem item = _typeMap.findItem(null);
    checkNotNull(item, _configType.getName());
    return item.getValue(_configType);
  }

  @Override
  public Collection<C> list() {
    throw new UnsupportedOperationException("list已废弃");
  }

  @Override
  public Iterable<C> all() {
    return allStream().collect(ImmutableList.toImmutableList());
  }

  @Override
  public Stream<C> allStream() {
    return _typeMap.getItems().stream().map(c -> c.getValue(_configType));
  }

  TypeMap _typeMap;

  Class<C> _configType;
}
