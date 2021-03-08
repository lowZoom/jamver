package luj.game.server.internal.luj.lujnet;

import io.netty.buffer.ByteBuf;
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

    ByteBuf frameBuf = ctx.getLastFrame();
//    LOG.debug("jam当前：{}，收到：{}", curRecv.getClass().getSimpleName(),
//        frameBuf == null ? -1 : frameBuf.readableBytes());

    JamFrameReceiveInvoker.Result result = invokeReceive(curRecv, connState, frameBuf);

    JamverNetReceiveFrame nextRecv = result.nextReceiver();
    connState.setNextReceiver(nextRecv);

    int waitBytes = result.waitByteCount();
//    LOG.debug("jam下一个：{}，等：{}", nextRecv == null ? "<无>"
//        : nextRecv.getClass().getSimpleName(), waitBytes);

    return ctx.then()
        .waitBytes(waitBytes)
        .nextReceiver(this);
  }

  private JamverNetReceiveFrame getCurrentReceiver(NetConnState connState) {
    JamverNetReceiveFrame nextRecv = connState.getNextReceiver();
    return (nextRecv == null) ? getHeadReceiver(connState) : nextRecv;
  }

  private JamverNetReceiveFrame getHeadReceiver(NetConnState connState) {
    JambeanInLujnet jambean = connState.getBindParam();
    return jambean.getFrameReceivePlugin();
  }

  private JamFrameReceiveInvoker.Result invokeReceive(JamverNetReceiveFrame curRecv,
      NetConnState connState, ByteBuf frameBuf) throws Exception {
    JamFrameReceiveInvoker.Result result = JamFrameReceiveInvoker.GET
        .invoke(curRecv, connState.getPluginState(), frameBuf);

    Object packet = result.resultPacket();
    if (packet != null) {
      return receivePacket(packet, connState, frameBuf);
    }
    return result;
  }

  private JamFrameReceiveInvoker.Result receivePacket(Object packet, NetConnState connState,
      ByteBuf frameBuf) throws Exception {
    ReceivePacketMsg msg = new ReceivePacketMsg(connState.getConnectionId(), packet);
    Tellable netRef = connState.getBindParam().getNetRef();
    netRef.tell(msg);

    return JamFrameReceiveInvoker.GET.invoke(
        getHeadReceiver(connState), connState.getPluginState(), frameBuf);
  }

//  private static final Logger LOG = LoggerFactory.getLogger(OnReceiveFrame.class);
}
