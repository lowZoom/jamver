package luj.game.server.internal.data.execute.save;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import luj.ava.collection.map.MapX;
import luj.cluster.api.actor.Tellable;
import luj.game.server.internal.data.instance.value.change.ChangedEncodable;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.save.create.DataCreateRequestorV2;
import luj.game.server.internal.data.types.HasOp;
import luj.game.server.internal.data.types.map.history.MapWithHistory;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgMap;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgSet;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DataUpdateMsg;
import scala.Tuple2;

public class CommandSaveRequestorV2 {

  public CommandSaveRequestorV2(Tellable saveRef, String idField, List<DataEntity> createLog,
      List<DataEntity> modifyLog) {
    _saveRef = saveRef;
    _idField = idField;
    _createLog = createLog;
    _modifyLog = modifyLog;
  }

  public void request() {
    //TODO: 不分开，作为一个事务一起发

    new DataCreateRequestorV2(_createLog, _idField, _saveRef).request();

    _modifyLog.stream()
        .filter(d -> !isTransient(d))
        .forEach(this::sendUpdateMsg);
  }

  private boolean isTransient(DataEntity dataObj) {
    return dataObj.getDataType().isTransient();
  }

  private void sendUpdateMsg(DataEntity dataObj) {
    MapWithHistory<String, Object> dataMap = dataObj.getFieldValueMap();

    // 获取数值变动
    Map<String, Object> updateHistory = MapX.nonNull(dataMap.getUpdateHistory());
    Map<String, Object> primitiveUpdated = updateHistory.entrySet().stream()
        .filter(e -> !(e.getValue() instanceof HasOp))
        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

    Map<String, Object> allValue = new HashMap<>(dataMap.getData());
    allValue.putAll(updateHistory);

    // 获取集合变动
    Map<Class<?>, Map<String, Object>> changedMap = getChangedByType(allValue.entrySet().stream()
        .filter(e -> e.getValue() instanceof HasOp)
        .map(e -> new Tuple2<>(e.getKey(), ((HasOp) e.getValue()).<ChangedEncodable>getDataOp()))
        .filter(t -> t._2.isChanged())
        .collect(toMap(t -> t._1, t -> t._2.encodeChanged())));

    if (primitiveUpdated.isEmpty() && changedMap.isEmpty()) {
      // 只有集合字段初始化，忽略
      return;
    }

    String dataType = dataObj.getDataType().getName();
    Comparable<?> dataId = dataObj.getDataId();

    DataUpdateMsg msg = new DataUpdateMsg(dataType, dataId, _idField, primitiveUpdated,
        getTypeMap(changedMap, DUpdateMsgSet.class), getTypeMap(changedMap, DUpdateMsgMap.class));

    _saveRef.tell(msg);
  }

  private Map<Class<?>, Map<String, Object>> getChangedByType(Map<String, Object> allMap) {
    Function<List<Map.Entry<String, Object>>, Map<String, Object>> listToMap =
        list -> list.stream().collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

    return allMap.entrySet().stream()
        .collect(groupingBy(e -> e.getValue().getClass()))
        .entrySet().stream()
        .collect(toMap(Map.Entry::getKey, e -> listToMap.apply(e.getValue())));
  }

  @SuppressWarnings("unchecked")
  private <T> Map<String, T> getTypeMap(
      Map<Class<?>, Map<String, Object>> changedMap, Class<T> type) {
    Map<String, Object> result = changedMap.get(type);
    if (result == null) {
      return ImmutableMap.of();
    }
    return (Map<String, T>) result;
  }

  private final Tellable _saveRef;
  private final String _idField;

  private final List<DataEntity> _createLog;
  private final List<DataEntity> _modifyLog;
}
