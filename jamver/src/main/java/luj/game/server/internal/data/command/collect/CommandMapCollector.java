package luj.game.server.internal.data.command.collect;

import static com.google.common.collect.ImmutableMap.toImmutableMap;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import luj.ava.reflect.type.TypeX;
import luj.ava.stream.StreamX;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import org.slf4j.LoggerFactory;

public class CommandMapCollector {

  public CommandMapCollector(Collection<GameDataCommand<?, ?>> commandList,
      Collection<GameDataLoad<?, ?>> loadList) {
    _commandList = commandList;
    _loadList = loadList;
  }

  public Map<String, GameplayDataActor.CommandKit> collect() {
    Map<String, GameplayDataActor.CommandKit> cmdMap = StreamX.from(_loadList)
        .map(this::makeKit)
        .collect(toImmutableMap(k -> k.getCommandType().getName(), Function.identity()));

    for (GameplayDataActor.CommandKit kit : cmdMap.values()) {
      KitImpl impl = (KitImpl) kit;
      impl._commandMap = cmdMap;
    }

    return cmdMap;
  }

  private KitImpl makeKit(GameDataLoad<?, ?> load) {
    KitImpl kit = new KitImpl();
    GameDataCommand<?, ?> cmd = findCommandBean(load.getClass());
    kit._command = cmd;

    Class<?> cmdType = cmd.getClass();
    TypeX<?> cmdDecl = TypeX.of(cmdType).getSupertype(GameDataCommand.class);
    kit._commandType = cmdType;
    kit._paramType = cmdDecl.getTypeParam(0).asClass();

    kit._loader = load;
    kit._loadResultType = cmdDecl.getTypeParam(1).asClass();

    kit._logger = LoggerFactory.getLogger(cmdType);
    return kit;
  }

  private GameDataCommand<?, ?> findCommandBean(Class<?> loadType) {
    return _commandList.stream()
        .filter(l -> l.getClass() == loadType.getDeclaringClass())
        .findAny()
        .orElseThrow(() -> new UnsupportedOperationException(loadType.getName()));
  }

  private final Collection<GameDataCommand<?, ?>> _commandList;

  private final Collection<GameDataLoad<?, ?>> _loadList;
}
