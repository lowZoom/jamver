package luj.game.server.internal.data.save.wait.add;

import static com.google.common.base.Preconditions.checkState;
import static java.util.stream.Collectors.toMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import luj.game.server.internal.data.cache.CacheKeyMaker;
import luj.game.server.internal.data.save.wait.BatchUpdateItem;
import luj.game.server.internal.data.save.wait.IoWaitBatch;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgMap;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgSet;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DataUpdateMsg;

public class IoWaitUpdateAdder {

  public IoWaitUpdateAdder(IoWaitBatch batch, DataUpdateMsg msg) {
    _batch = batch;
    _msg = msg;
  }

  public void add() {
    //TODO: 如果数据在同一批次里创建，还可以将更新直接并进创建动作里

    String dataType = _msg.getDataType();
    Comparable<?> dataId = _msg.getDataId();
    String cacheKey = new CacheKeyMaker(dataType, dataId).make();

    Map<String, BatchUpdateItem> batchMap = _batch.getUpdateMap();
    BatchUpdateItem oldItem = batchMap.get(cacheKey);

    if (oldItem != null) {
      mergeItem(oldItem);
      return;
    }

    Map<String, DUpdateMsgSet> setUpdated = _msg.getSetUpdated().entrySet().stream()
        .collect(toMap(Map.Entry::getKey, e -> setToMutable(e.getValue())));

    Map<String, DUpdateMsgMap> mapUpdated = _msg.getMapUpdated().entrySet().stream()
        .collect(toMap(Map.Entry::getKey, e -> mapToMutable(e.getValue())));

    BatchUpdateItem newItem = new BatchUpdateItem(dataType, dataId, _msg.getIdField(),
        new HashMap<>(_msg.getPrimitiveUpdated()),
        new HashMap<>(setUpdated),
        new HashMap<>(mapUpdated));

    batchMap.put(cacheKey, newItem);
  }

  private DUpdateMsgSet setToMutable(DUpdateMsgSet src) {
    Set<Object> add = new LinkedHashSet<>(src.getAddHistory());
    Set<Object> remove = new LinkedHashSet<>(src.getRemoveHistory());
    return new DUpdateMsgSet(add, remove);
  }

  private DUpdateMsgMap mapToMutable(DUpdateMsgMap src) {
    Map<Object, Object> update = new HashMap<>(src.getUpdateHistory());
    Set<Object> remove = new HashSet<>(src.getRemoveHistory());
    return new DUpdateMsgMap(update, remove);
  }

  private void mergeItem(BatchUpdateItem oldItem) {
    String oldType = oldItem.getDataType();
    String newType = _msg.getDataType();
    checkState(oldType.equals(newType), "OLD:%s, NEW:%s", oldType, newType);

    oldItem.getPrimitiveUpdated().putAll(_msg.getPrimitiveUpdated());

    Map<String, DUpdateMsgSet> setFields = oldItem.getSetUpdated();
    for (Map.Entry<String, DUpdateMsgSet> e : _msg.getSetUpdated().entrySet()) {
      setMerge(setFields, e.getKey(), e.getValue());
    }

    Map<String, DUpdateMsgMap> mapFields = oldItem.getMapUpdated();
    for (Map.Entry<String, DUpdateMsgMap> e : _msg.getMapUpdated().entrySet()) {
      mapMerge(mapFields, e.getKey(), e.getValue());
    }
  }

  private void setMerge(Map<String, DUpdateMsgSet> setFields, String field, DUpdateMsgSet value) {
    DUpdateMsgSet oldVal = setFields.get(field);
    if (oldVal == null) {
      setFields.put(field, setToMutable(value));
      return;
    }

    oldVal.getAddHistory().addAll(value.getAddHistory());
    oldVal.getRemoveHistory().addAll(value.getRemoveHistory());
  }

  private void mapMerge(Map<String, DUpdateMsgMap> mapFields, String field, DUpdateMsgMap value) {
    DUpdateMsgMap oldVal = mapFields.get(field);
    if (oldVal == null) {
      mapFields.put(field, mapToMutable(value));
      return;
    }

    oldVal.getUpdateHistory().putAll(value.getUpdateHistory());
    oldVal.getRemoveHistory().addAll(value.getRemoveHistory());
  }

  private final IoWaitBatch _batch;

  private final DataUpdateMsg _msg;
}
