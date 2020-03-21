package luj.game.server.internal.data.load.io;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import luj.cluster.api.actor.ActorPreStartHandler;
import luj.game.server.api.data.annotation.Transient;
import luj.game.server.api.plugin.JamverDataLoadLoad;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.load.LoadFinishMsg;

public class DataIoLoader {

  public DataIoLoader(JamverDataLoadLoad loadPlugin, Object loadState, Class<?> dataType,
      Comparable<?> dataId, String dataIdField, ActorPreStartHandler.Actor dataRef) {
    _loadPlugin = loadPlugin;
    _loadState = loadState;
    _dataType = dataType;
    _dataId = dataId;
    _dataIdField = dataIdField;
    _dataRef = dataRef;
  }

  /**
   * @see luj.game.server.internal.luj.lujcluster.actor.gameplay.data.cache.load.OnLoadFinish
   */
  public void load() {
    Map<String, Object> valueMap = findData();
    boolean present = !valueMap.isEmpty();

    LoadFinishMsg msg = new LoadFinishMsg(_dataType, _dataId, present, valueMap);
    _dataRef.tell(msg);
  }

  private Map<String, Object> findData() {
    if (_dataType.isAnnotationPresent(Transient.class)) {
      return ImmutableMap.of();
    }

    return ioLoad();
  }

  private Map<String, Object> ioLoad() {
    DataRequestImpl dataReq = new DataRequestImpl(_dataType, _dataId, _dataIdField);
    LoadContextImpl ctx = new LoadContextImpl(_loadState, dataReq);

    Map<String, Object> result = _loadPlugin.onLoad(ctx);
    if (result == null) {
      return ImmutableMap.of();
    }
    return ImmutableMap.copyOf(result);
  }

  private final JamverDataLoadLoad _loadPlugin;
  private final Object _loadState;

  private final Class<?> _dataType;
  private final Comparable<?> _dataId;
  private final String _dataIdField;

  private final ActorPreStartHandler.Actor _dataRef;
}
