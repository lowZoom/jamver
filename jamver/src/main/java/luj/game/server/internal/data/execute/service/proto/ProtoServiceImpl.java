package luj.game.server.internal.data.execute.service.proto;

import java.util.function.BiConsumer;
import luj.bean.api.BeanContext;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.service.CommandService;
import luj.game.server.internal.data.service.param.CommandParamMaker;

final class ProtoServiceImpl implements GameDataCommand.Proto {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T create(Class<T> protoType, BiConsumer<CommandService.Param, T> protoValue) {
    return (T) new CommandParamMaker(protoType,
        (BiConsumer<CommandService.Param, Object>) protoValue, _lujbean).make();
  }

  BeanContext _lujbean;
}
