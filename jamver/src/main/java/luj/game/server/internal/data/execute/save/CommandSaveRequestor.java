package luj.game.server.internal.data.execute.save;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import luj.ava.collection.map.MapX;
import luj.cluster.api.actor.Tellable;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.instance.value.ChangedEncodable;
import luj.game.server.internal.data.save.DataTransientChecker;
import luj.game.server.internal.data.save.create.request.DataCreateRequestor;
import luj.game.server.internal.data.types.HasOp;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgMap;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgSet;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DataUpdateMsg;
import scala.Tuple2;

public class CommandSaveRequestor {

  public CommandSaveRequestor(Tellable saveRef, List<DataTempProxy> createLog,
      List<DataTempProxy> modifyLog) {
    _saveRef = saveRef;
    _createLog = createLog;
    _modifyLog = modifyLog;
  }

  public void request() {
    //TODO: 不分开，作为一个事务一起发

    new DataCreateRequestor(_createLog, _saveRef).request();

    _modifyLog.stream()
        .filter(d -> !isTransient(d))
        .forEach(this::sendUpdateMsg);
  }

  private boolean isTransient(DataTempProxy dataObj) {
    return DataTransientChecker.GET.check(dataObj);
  }

  private void sendUpdateMsg(DataTempProxy dataObj) {
    Map<String, Object> dataMap = dataObj.getDataMap();
    Comparable<?> dataId = (Comparable<?>) dataMap.get(DataTempProxy.ID);

    // 获取数值变动
    Map<String, Object> primitiveUpdated = ImmutableMap.copyOf(
        MapX.nonNull(dataObj.getDataMapV2().getUpdateHistory()));

    // 获取集合变动
    Map<Class<?>, Map<String, Object>> changedMap = getChangedByType(dataMap.entrySet().stream()
        .filter(e -> e.getValue() instanceof HasOp)
        .map(e -> new Tuple2<>(e.getKey(), ((HasOp) e.getValue()).<ChangedEncodable>getDataOp()))
        .filter(t -> t._2.isChanged())
        .collect(toMap(t -> t._1, t -> t._2.encodeChanged())));

    Class<?> dataType = dataObj.getDataType();
    DataUpdateMsg msg = new DataUpdateMsg(dataType, dataId, DataTempProxy.ID, primitiveUpdated,
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

  private final List<DataTempProxy> _createLog;
  private final List<DataTempProxy> _modifyLog;
}
