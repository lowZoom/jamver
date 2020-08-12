package luj.game.server.internal.data.service.param;

import java.util.function.BiConsumer;
import luj.bean.api.BeanContext;
import luj.bean.api.bean.Bean;
import luj.game.server.api.data.service.CommandService;

public class CommandParamMaker {

  public CommandParamMaker(Class<?> paramType,
      BiConsumer<CommandService.Param, Object> buildInvoker, BeanContext lujbean) {
    _paramType = paramType;
    _buildInvoker = buildInvoker;
    _lujbean = lujbean;
  }

  public Object make() {
    Bean<?> paramBean = _lujbean.createBean(_paramType);

    ParamImpl<?> builder = new ParamImpl<>();
    builder._paramBean = paramBean;

    _buildInvoker.accept(builder, paramBean.getSetterInstance());
    return paramBean.getValueInstance();
  }

  private final Class<?> _paramType;
  private final BiConsumer<CommandService.Param, Object> _buildInvoker;

  private final BeanContext _lujbean;
}
