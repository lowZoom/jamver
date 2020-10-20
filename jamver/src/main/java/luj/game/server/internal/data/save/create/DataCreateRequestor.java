package luj.game.server.internal.data.save.create;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import luj.cluster.api.actor.Tellable;
import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.instance.value.create.CreatedEncodable;
import luj.game.server.internal.data.save.DataTransientChecker;
import luj.game.server.internal.data.types.HasOp;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.create.DataCreateMsg;
import scala.Tuple2;

public class DataCreateRequestor {

  public DataCreateRequestor(List<DataTempProxy> createList, Tellable saveRef) {
    _createList = createList;
    _saveRef = saveRef;
  }

  public void request() {
    _createList.stream()
        .filter(d -> !isTransient(d))
        .forEach(this::sendCreateMsg);
  }

  private boolean isTransient(DataTempProxy dataObj) {
    return DataTransientChecker.GET.check(dataObj);
  }

  private void sendCreateMsg(DataTempProxy dataObj) {
    Map<String, Object> fieldMap = dataObj.getDataMapV2().getData();
    Map<String, Object> initMap = new HashMap<>(fieldMap);

    // 集合类的要拷贝一份，与缓存数据隔开，避免被修改
    fieldMap.entrySet().stream()
        .filter(e -> e.getValue() instanceof HasOp)
        .map(e -> new Tuple2<>(e.getKey(), ((HasOp) e.getValue()).<CreatedEncodable>getDataOp()))
        .forEach(t -> initMap.put(t._1, t._2.encodeInit()));

    DataCreateMsg msg = new DataCreateMsg(dataObj.getDataType(), initMap);
    _saveRef.tell(msg);
  }

  private final List<DataTempProxy> _createList;

  private final Tellable _saveRef;
}
