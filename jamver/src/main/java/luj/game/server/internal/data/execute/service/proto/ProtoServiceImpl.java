package luj.game.server.internal.data.execute.service.proto;

import java.util.function.BiFunction;
import luj.bean.api.BeanContext;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.service.CommandService;
import luj.game.server.internal.data.service.param.CommandParamMaker;

final class ProtoServiceImpl implements GameDataCommand.Proto {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T create(Class<T> protoType,
      BiFunction<CommandService.Param, T, CommandService.Param> protoValue) {
    return (T) new CommandParamMaker(protoType,
        (b, p) -> protoValue.apply(b, (T) p), _lujbean).make();
  }

  BeanContext _lujbean;
}
