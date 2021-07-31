package luj.game.server.internal.data.execute.service.data;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.service.CommandService;
import luj.game.server.internal.data.command.service.CommandServiceFactory;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.load.result.DataResultProxy;
import luj.game.server.internal.data.load.result.DataResultProxyV2;
import luj.game.server.internal.data.service.set.FieldImpl2;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;

public class DataServiceImpl implements GameDataCommand.Data {

  public DataServiceImpl(Tellable dataRef, List<DataEntity> createLog,
      ServerMessageHandler.Server remoteRef, Map<String, GameplayDataActor.CommandKit> commandMap,
      BeanContext lujbean) {
    _dataRef = dataRef;
    _createLog = createLog;
    _remoteRef = remoteRef;
    _commandMap = commandMap;
    _lujbean = lujbean;
  }

  public void specifySetField(DataResultProxyV2 data, String fieldName) {
    _curData = data;
    _curField = fieldName;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T create(Class<T> dataType) {
    return (T) DataCreateRunner.GET.run(dataType, _createLog, this::specifySetField);
  }

  /**
   * @see DataResultProxy#invoke
   * @see #specifySetField
   */
  @Override
  public <T> void set(Supplier<T> field, T value) {
    set(field).$(value);
  }

  @Override
  public <T> Field<T> set(Supplier<T> field) {
    field.get();
    return new FieldImpl2<>(_curData, _curField);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends Comparable<T>> T id(Object data) {
    return (T) DataIdRunner.GET.run(data);
  }

  @Override
  public boolean exists(Object data) {
    //FIXME: TEMP
    return data != null;
  }

  @Override
  public <P> CommandService<P> command(Class<? extends GameDataCommand<P, ?>> commandType) {
    return new CommandServiceFactory(_lujbean, _dataRef,
        _remoteRef, commandType, _commandMap).create();
  }

  @Override
  public <P> void executeCommand(Class<? extends GameDataCommand<P, ?>> commandType) {
    command(commandType).execute((b, p) -> b);
  }

//  private static final Logger LOG = LoggerFactory.getLogger(DataServiceImpl.class);

  private DataResultProxyV2 _curData;
  private String _curField;

  private final Tellable _dataRef;
  private final List<DataEntity> _createLog;

  private final ServerMessageHandler.Server _remoteRef;
  private final Map<String, GameplayDataActor.CommandKit> _commandMap;

  private final BeanContext _lujbean;
}
