package luj.game.server.internal.data.command.collect;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import luj.ava.reflect.type.TypeX;
import luj.ava.stream.StreamX;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataLoad;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import org.slf4j.LoggerFactory;

public class CommandMapCollector {

  public CommandMapCollector(List<GameDataCommand<?, ?>> commandList,
      List<GameDataLoad<?, ?>> loadList) {
    _commandList = commandList;
    _loadList = loadList;
  }

  public Map<Class<?>, GameplayDataActor.CommandKit> collect() {
    return StreamX.from(_loadList)
        .map(this::makeKit)
        .collect(toMap(KitImpl::getCommandType, Function.identity()));
  }

  private KitImpl makeKit(GameDataLoad<?, ?> load) {
    KitImpl kit = new KitImpl();
    GameDataCommand<?, ?> cmd = findCommandBean(load.getClass());
    kit._command = cmd;

    Class<?> cmdType = cmd.getClass();
    kit._commandType = cmdType;
    kit._logger = LoggerFactory.getLogger(cmdType);

    kit._loader = load;
    kit._loadResultType = TypeX.ofInstance(load)
        .getSupertype(GameDataLoad.class)
        .getTypeParam(1)
        .asClass();

    return kit;
  }

  private GameDataCommand<?, ?> findCommandBean(Class<?> loadType) {
    return _commandList.stream()
        .filter(l -> l.getClass() == loadType.getDeclaringClass())
        .findAny()
        .orElseThrow(() -> new UnsupportedOperationException(loadType.getName()));
  }

  private final List<GameDataCommand<?, ?>> _commandList;

  private final List<GameDataLoad<?, ?>> _loadList;
}
