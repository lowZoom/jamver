package luj.game.server.internal.event.instancev2;

import java.util.function.BiConsumer;
import luj.bean.api.BeanContext;
import luj.bean.api.bean.Bean;
import luj.game.server.api.data.GameDataCommand;

public class EventInstanceMakerV2 {

  public EventInstanceMakerV2(BeanContext lujbean, Class<?> eventType,
      BiConsumer<GameDataCommand.Event.Instance, Object> buildCaller) {
    _lujbean = lujbean;
    _eventType = eventType;
    _buildCaller = buildCaller;
  }

  public Object make() {
    EventInstanceImpl<?> builder = new EventInstanceImpl<>();
    Bean<?> eventBean = _lujbean.createBean(_eventType);

    builder._event = eventBean;
    _buildCaller.accept(builder, eventBean.getSetterInstance());

    return eventBean.getValueInstance();
  }

  private final BeanContext _lujbean;
  private final Class<?> _eventType;

  private final BiConsumer<GameDataCommand.Event.Instance, Object> _buildCaller;
}
