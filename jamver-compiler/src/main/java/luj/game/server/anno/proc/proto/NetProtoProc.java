package luj.game.server.anno.proc.proto;

import com.google.auto.service.AutoService;
import java.io.IOException;
import java.lang.annotation.Annotation;
import javax.annotation.processing.Processor;
import luj.game.server.api.net.NetProto;
import luj.generate.annotation.process.SingleAnnoProc;
import luj.proto.anno.proc.proto.meta.ProtoMetaGenerator;

@AutoService(Processor.class)
public final class NetProtoProc extends SingleAnnoProc {

  @Override
  protected Class<? extends Annotation> supportedAnnotationType() {
    return NetProto.class;
  }

  @Override
  protected void processElement(Context ctx) throws IOException {
    ProtoMetaGenerator.Factory.create(ctx).generate();
  }
}
