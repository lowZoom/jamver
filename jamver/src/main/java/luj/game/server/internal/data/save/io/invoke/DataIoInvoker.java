package luj.game.server.internal.data.save.io.invoke;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.stream.Collectors.toMap;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Map;
import luj.game.server.api.plugin.JamverDataSaveIo;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.create.DataCreateMsg;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgMap;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DUpdateMsgSet;
import luj.game.server.internal.luj.lujcluster.actor.gameplay.data.save.update.DataUpdateMsg;

public class DataIoInvoker {

  public DataIoInvoker(JamverDataSaveIo<?> ioPlugin, Object saveState,
      List<DataCreateMsg> createList, List<DataUpdateMsg> updateList) {
    _ioPlugin = ioPlugin;
    _saveState = saveState;
    _createList = createList;
    _updateList = updateList;
  }

  public void invoke() {
    IoContextImpl ctx = new IoContextImpl();
    ctx._saveState = _saveState;

    ctx._created = ImmutableList.of();// _createList.stream()
//        .collect(.toli)

    ctx._changed = _updateList.stream()
        .map(this::wrapUpdate)
        .collect(toImmutableList());

    _ioPlugin.onIo(ctx);
  }

  private ChangedImpl wrapUpdate(DataUpdateMsg msg) {
    ChangedImpl changed = new ChangedImpl();
    changed._msg = msg;

    IdImpl id = new IdImpl();
    id._msg = msg;
    changed._id = id;

    changed._set = msg.getSetUpdated().entrySet().stream()
        .collect(toMap(Map.Entry::getKey, e -> wrapSet(e.getValue())));

    changed._map = msg.getMapUpdated().entrySet().stream()
        .collect(toMap(Map.Entry::getKey, e -> wrapMap(e.getValue())));

    return changed;
  }

  private CSetImpl wrapSet(DUpdateMsgSet msg) {
    CSetImpl set = new CSetImpl();
    set._msg = msg;
    return set;
  }

  private CMapImpl wrapMap(DUpdateMsgMap msg) {
    CMapImpl map = new CMapImpl();
    map._msg = msg;
    return map;
  }

  private final JamverDataSaveIo<?> _ioPlugin;
  private final Object _saveState;

  private final List<DataCreateMsg> _createList;
  private final List<DataUpdateMsg> _updateList;
}
