package luj.game.server.internal.data.execute.group;

import java.util.List;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataCommandGroup;

final class ContextImpl implements GameDataCommandGroup.Context {

  @Override
  public List<GameDataCommandGroup.Element<?>> elements() {
    return _elemList;
  }

  @Override
  public <P> P getParam(GameDataCommandGroup.Element<? extends GameDataCommand<P, ?>> elem) {
    return (P) ((ElementImpl<?>) elem)._cmdParam;
  }

  @Override
  public <D> D getData(GameDataCommandGroup.Element<? extends GameDataCommand<?, D>> elem) {
    return (D) ((ElementImpl<?>) elem)._resultProxy.getInstance();
  }

  List<GameDataCommandGroup.Element<?>> _elemList;
}
