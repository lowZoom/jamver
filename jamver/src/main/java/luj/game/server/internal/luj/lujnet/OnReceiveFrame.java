package luj.game.server.internal.luj.lujnet;

import luj.ava.spring.Internal;
import luj.cluster.api.actor.Tellable;
import luj.game.server.api.plugin.JamverNetReceiveFrame;
import luj.game.server.internal.luj.lujcluster.actor.network.receive.ReceivePacketMsg;
import luj.game.server.internal.network.receive.frame.JamFrameReceiveInvoker;
import luj.net.api.server.FrameDataReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
final class OnReceiveFrame implements FrameDataReceiver {

  @Override
  public Result receive(Context ctx) throws Exception {
    NetConnState connState = ctx.getConnectionState();
    JamverNetReceiveFrame curRecv = getCurrentReceiver(connState);
    Object lastFrame = ctx.getLastFrame();

    JamFrameReceiveInvoker.Result result = JamFrameReceiveInvoker.GET.invoke(curRecv, lastFrame);
    JamverNetReceiveFrame nextRecv = result.nextReceiver();
    connState.setNextReceiver(nextRecv);

    tryReceivePacket(result, connState);

    LOG.debug("jam当前：{}，下一个：{}", curRecv.getClass().getSimpleName(),
        nextRecv == null ? "<无>" : nextRecv.getClass().getSimpleName());

    return ctx.then()
        .waitBytes(result.waitByteCount())
        .nextReceiver(this);
  }

  private JamverNetReceiveFrame getCurrentReceiver(NetConnState connState) {
    JamverNetReceiveFrame nextRecv = connState.getNextReceiver();
    if (nextRecv != null) {
      return nextRecv;
    }

    JambeanInLujnet jambean = connState.getBindParam();
    return jambean.getFramePlugin();
  }

  private void tryReceivePacket(JamFrameReceiveInvoker.Result result, NetConnState connState) {
    Object packet = result.resultPacket();
    if (packet == null) {
      return;
    }

    ReceivePacketMsg msg = new ReceivePacketMsg(packet);
    Tellable netRef = connState.getBindParam().getNetRef();
    netRef.tell(msg);
  }

  private static final Logger LOG = LoggerFactory.getLogger(OnReceiveFrame.class);
}
