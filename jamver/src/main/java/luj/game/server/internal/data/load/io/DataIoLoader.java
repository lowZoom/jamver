package luj.game.server.internal.data.load.io;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.api.data.annotation.Transient;
import luj.game.server.api.plugin.JamverDataLoadIo;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.load.LoadFinishMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataIoLoader {

  public DataIoLoader(Class<?> dataType, Comparable<?> dataId, String idField,
      ExecutorService ioRunner, JamverDataLoadIo loadPlugin, Object loadState,
      ActorPreStartHandler.Actor dataRef) {
    _dataType = dataType;
    _dataId = dataId;
    _idField = idField;
    _ioRunner = ioRunner;
    _loadPlugin = loadPlugin;
    _loadState = loadState;
    _dataRef = dataRef;
  }

  /**
   * @see luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.load.OnLoadFinish
   */
  public void load() {
    if (_dataType.isAnnotationPresent(Transient.class)) {
      finishLoad(ImmutableMap.of());
      return;
    }

    //TODO: 考虑用io队列把读取合批，类似写入

    _ioRunner.submit(this::runIo);
  }

  private void runIo() {
    try {
      Map<String, Object> valueMap = ioLoad();
      finishLoad(valueMap);

    } catch (Throwable e) {
      LOG.error(e.getMessage(), e);
      //TODO: 考虑出错重试
    }
  }

  private Map<String, Object> ioLoad() {
    DataRequestImpl dataReq = new DataRequestImpl(_dataType, _dataId, _idField);
    LoadContextImpl ctx = new LoadContextImpl(_loadState, dataReq);

    Map<String, Object> result = _loadPlugin.onLoad(ctx);
    if (result == null) {
      return ImmutableMap.of();
    }
    return ImmutableMap.copyOf(result);
  }

  private void finishLoad(Map<String, Object> valueMap) {
    boolean present = !valueMap.isEmpty();
    LoadFinishMsg msg = new LoadFinishMsg(_dataType, _dataId, present, valueMap);
    _dataRef.tell(msg);
  }

  private static final Logger LOG = LoggerFactory.getLogger(DataIoLoader.class);

  private final Class<?> _dataType;
  private final Comparable<?> _dataId;
  private final String _idField;

  private final ExecutorService _ioRunner;
  private final JamverDataLoadIo _loadPlugin;
  private final Object _loadState;

  private final ActorPreStartHandler.Actor _dataRef;
}
