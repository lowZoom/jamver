package luj.game.server.internal.data.execute.service.data;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import luj.bean.api.BeanContext;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.service.CommandService;
import luj.game.server.internal.data.command.service.CommandServiceFactory;
import luj.game.server.internal.data.instance.DataInstanceCreator;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.load.result.DataResultFactory;
import luj.game.server.internal.data.load.result.DataResultProxy;
import luj.game.server.internal.data.service.set.FieldImpl;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.GameplayDataActor;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.DatacmdExecMsg;

public class DataServiceImpl implements GameDataCommand.Data {

  public DataServiceImpl(Tellable dataRef, List<DataTempProxy> createLog,
      ServerMessageHandler.Server remoteRef, Map<Class<?>, GameplayDataActor.CommandKit> commandMap,
      BeanContext lujbean) {
    _dataRef = dataRef;
    _createLog = createLog;
    _remoteRef = remoteRef;
    _commandMap = commandMap;
    _lujbean = lujbean;
  }

  public void specifySetField(DataResultProxy data, String fieldName) {
    _curData = data;
    _curField = fieldName;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T create(Class<T> dataType) {
    DataTempProxy dataObj = new DataInstanceCreator(dataType).create();
    _createLog.add(dataObj);

    DataResultProxy result = new DataResultFactory(dataObj, this::specifySetField).create();
    return (T) result.getInstance();
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
    return new FieldImpl<>(_curData, _curField);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends Comparable<T>> T id(Object data) {
    DataResultProxy proxy = (DataResultProxy) Proxy.getInvocationHandler(data);
    return (T) proxy.getData().getDataMap().get(DataTempProxy.ID);
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
    executeCommand(commandType, null);
  }

  @Override
  public <P> void executeCommand(Class<? extends GameDataCommand<P, ?>> commandType, P param) {
    DatacmdExecMsg msg = new DatacmdExecMsg(commandType, param, _remoteRef);
    _dataRef.tell(msg);
  }

//  private static final Logger LOG = LoggerFactory.getLogger(DataServiceImpl.class);

  private DataResultProxy _curData;
  private String _curField;

  private final Tellable _dataRef;
  private final List<DataTempProxy> _createLog;

  private final ServerMessageHandler.Server _remoteRef;
  private final Map<Class<?>, GameplayDataActor.CommandKit> _commandMap;

  private final BeanContext _lujbean;
}
