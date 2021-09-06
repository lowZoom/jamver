package luj.game.server.internal.data.service.param;

import java.util.function.BiConsumer;
import luj.bean.api.BeanContext;
import luj.bean.api.bean.Bean;
import luj.game.server.api.data.service.CommandService;

public class CommandParamMaker {

  public CommandParamMaker(Class<?> paramType,
      BiConsumer<CommandService.Param, Object> buildCaller, BeanContext lujbean) {
    _paramType = paramType;
    _buildCaller = buildCaller;
    _lujbean = lujbean;
  }

  public Object make() {
    ParamImpl<?> builder = new ParamImpl<>();
    Bean<?> paramBean = _lujbean.createBean(_paramType);

    builder._paramBean = paramBean;
    _buildCaller.accept(builder, paramBean.getSetterInstance());

    return paramBean.getValueInstance();
  }

  private final Class<?> _paramType;
  private final BiConsumer<CommandService.Param, Object> _buildCaller;

  private final BeanContext _lujbean;
}
