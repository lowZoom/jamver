package luj.game.server.internal.data.command.queue.wake;

import java.util.List;
import luj.cache.api.request.CacheRequest;

public interface QueueWakeBehavior {

  List<CacheRequest> getCacheReq();

  void finishExec();
}
