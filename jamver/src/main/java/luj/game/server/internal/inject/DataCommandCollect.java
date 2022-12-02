package luj.game.server.internal.inject;

import com.google.common.collect.ImmutableList;
import java.util.List;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.GameDataCommandGroup;
import luj.game.server.api.data.GameDataLoad;
import luj.spring.anno.Internal;
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
  List<GameDataCommand<?, ?>> _dataCommandList = ImmutableList.of();

  @Autowired(required = false)
  List<GameDataLoad<?, ?>> _dataLoadList = ImmutableList.of();

  @Autowired(required = false)
  List<GameDataCommandGroup> _commandGroupList = ImmutableList.of();
}
