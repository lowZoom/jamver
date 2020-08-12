package luj.game.server.internal.data.execute;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.cluster.ServerMessageHandler;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.api.data.service.CommandService;
import luj.game.server.internal.data.instance.DataInstanceCreator;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.load.result.DataResultProxy;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.execute.DatacmdExecMsg;

public class DataServiceImpl implements GameDataCommand.Data {

  public DataServiceImpl(Tellable dataRef, List<DataTempProxy> createLog,
      ServerMessageHandler.Server remoteRef) {
    _dataRef = dataRef;
    _createLog = createLog;
    _remoteRef = remoteRef;
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

    DataResultProxy result = new DataResultProxy(dataObj, this::specifySetField).init();
    return (T) result.getInstance();
  }

  /**
   * @see DataResultProxy#invoke
   * @see #specifySetField
   */
  @Override
  public <T> void set(Supplier<T> field, T value) {
    field.get();

    _curData.setDirty(true);
    _curData.getData().getDataMap().put(_curField, value);
  }

  @Override
  public <T> void add(Supplier<Collection<T>> field, T element) {
    Collection<T> c = field.get();

    _curData.setDirty(true);
    c.add(element);
  }

  @Override
  public boolean exists(Object data) {
    //FIXME: TEMP
    return data != null;
  }

  @Override
  public <P> CommandService<P> command(Class<? extends GameDataCommand<P, ?>> commandType) {
    throw new UnsupportedOperationException("command还没实现");
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
}
