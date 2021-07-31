package luj.game.server.internal.data.execute.finish;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.execute.load.request.node.LoadNodeOp;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.load.result.DataResultProxyV2;
import luj.game.server.internal.data.load.result.LoadResultProxy;

public class ExecFinishWalker implements RequestWalkListener {

  public ExecFinishWalker(CacheContainer dataCache, LoadResultProxy loadResult,
      List<DataEntity> loadLog, DataResultProxyV2.FieldHook fieldHook) {
    _dataCache = dataCache;
    _loadResult = loadResult;
    _loadLog = loadLog;
    _fieldHook = fieldHook;
  }

  /**
   * @see CommandExecFinisher#finish
   */
  @Override
  public Object onWalk(Context ctx) {
    LoadNodeOp nodeOp = ctx.getNode();
    LoadNodeOp.Data data = nodeOp.findWhenFinish(ctx, _loadLog, _fieldHook);

    checkNotNull(data, nodeOp);
    ctx.getFieldSetter().accept(_loadResult, data.getResult());

    return data.getReturn();
  }

//  private static final Logger LOG = LoggerFactory.getLogger(FinishWalker.class);

  private final CacheContainer _dataCache;

  private final LoadResultProxy _loadResult;
  private final List<DataEntity> _loadLog;

  private final DataResultProxyV2.FieldHook _fieldHook;
}
