package luj.game.server.internal.data.save.io.invoke;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.stream.Collectors.toMap;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import luj.game.server.api.plugin.JamverDataSaveIo;
import luj.game.server.internal.data.save.wait.BatchCreateItem;
import luj.game.server.internal.data.save.wait.BatchUpdateItem;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgMap;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgSet;

public class DataIoInvoker {

  public DataIoInvoker(JamverDataSaveIo<?> ioPlugin, Object saveState,
      List<BatchCreateItem> createList, List<BatchUpdateItem> updateList) {
    _ioPlugin = ioPlugin;
    _saveState = saveState;
    _createList = createList;
    _updateList = updateList;
  }

  public void invoke() {
    IoContextImpl ctx = new IoContextImpl();
    ctx._saveState = _saveState;

    ctx._created = _createList.stream()
        .map(this::wrapCreate)
        .collect(toImmutableList());

    ctx._changed = _updateList.stream()
        .map(this::wrapUpdate)
        .collect(toImmutableList());

    _ioPlugin.onIo(ctx);
  }

  private CreatedImpl wrapCreate(BatchCreateItem item) {
    CreatedImpl created = new CreatedImpl();
    created._item = item;

    IdImpl id = new IdImpl();
    id._dataId = item.getDataId();
    id._idField = item.getIdField();
    created._id = id;

    Set<Map.Entry<String, Object>> datas = item.getDataValue().entrySet();
    created._set = datas.stream()
        .filter(e -> e.getValue() instanceof Set)
        .collect(toMap(Map.Entry::getKey, e -> ImmutableSet.copyOf((Set<?>) e.getValue())));

    created._map = datas.stream()
        .filter(e -> e.getValue() instanceof Map)
        .collect(toMap(Map.Entry::getKey, e -> ImmutableMap.copyOf((Map<?, ?>) e.getValue())));

    created._primitive = datas.stream()
        .filter(e -> !created._set.containsKey(e.getKey()))
        .filter(e -> !created._map.containsKey(e.getKey()))
        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

    return created;
  }

  private ChangedImpl wrapUpdate(BatchUpdateItem item) {
    ChangedImpl changed = new ChangedImpl();
    changed._item = item;

    IdImpl id = new IdImpl();
    id._dataId = item.getDataId();
    id._idField = item.getIdField();
    changed._id = id;

    changed._set = item.getSetUpdated().entrySet().stream()
        .collect(toMap(Map.Entry::getKey, e -> wrapSet(e.getValue())));

    changed._map = item.getMapUpdated().entrySet().stream()
        .collect(toMap(Map.Entry::getKey, e -> wrapMap(e.getValue())));

    return changed;
  }

  private ChSetImpl wrapSet(DUpdateMsgSet msg) {
    ChSetImpl set = new ChSetImpl();
    set._msg = msg;
    return set;
  }

  private ChMapImpl wrapMap(DUpdateMsgMap msg) {
    ChMapImpl map = new ChMapImpl();
    map._msg = msg;
    return map;
  }

  private final JamverDataSaveIo<?> _ioPlugin;
  private final Object _saveState;

  private final List<BatchCreateItem> _createList;
  private final List<BatchUpdateItem> _updateList;
}
