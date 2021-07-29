package luj.game.server.internal.inject;

import java.util.List;
import luj.ava.spring.Internal;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.api.data.GameDataLoad;
import org.springframework.beans.factory.annotation.Autowired;

@Internal
public class DataCommandCollect {

  public List<GameDataCommand<?, ?>> getDataCommandList() {
    return _dataCommandList;
  }

  public List<GameDataLoad<?, ?>> getDataLoadList() {
    return _dataLoadList;
  }

  public List<GameDataCommandGroup> getCommandGroupList() {
    return _commandGroupList;
  }

  @Autowired(required = false)
  private List<GameDataCommand<?, ?>> _dataCommandList;

  @Autowired(required = false)
  private List<GameDataLoad<?, ?>> _dataLoadList;

  @Autowired(required = false)
  private List<GameDataCommandGroup> _commandGroupList;
}
