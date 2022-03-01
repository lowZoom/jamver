package luj.game.server.internal.data.execute.load.missing;

import java.util.List;
import luj.cache.api.container.CacheContainer;
import luj.cache.api.request.RequestWalkListener;
import luj.game.server.internal.data.cache.CacheItem;
import luj.game.server.internal.data.execute.load.missing.log.MissingLog;
import luj.game.server.internal.data.execute.load.request.node.LoadNodeOp;

final class ReadyWalker implements RequestWalkListener {

  /**
   * @see luj.game.server.internal.data.execute.finish.ExecFinishWalker#onWalk
   */
  @Override
  public Object onWalk(Context ctx) {
    LoadNodeOp nodeOp = ctx.getNode();
    return nodeOp.findWhenReady(ctx, _missingOut, _lockedOrLoadingOut);
  }

  List<CacheItem> _lockedOrLoadingOut;

  MissingLog _missingOut;
}
