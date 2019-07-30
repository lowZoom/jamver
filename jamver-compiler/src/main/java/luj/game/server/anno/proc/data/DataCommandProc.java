package luj.game.server.anno.proc.data;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.lang.annotation.Annotation;
import javax.annotation.processing.Processor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import luj.game.server.anno.proc.data.internal.LoadResultImplGenerator;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.data.load.DataLoadMetaHolder;
import luj.generate.annotation.process.SingleAnnoProc;
import luj.generate.annotation.process.type.ProcType;
import org.springframework.stereotype.Component;

@AutoService(Processor.class)
public final class DataCommandProc extends SingleAnnoProc {

  @Override
  protected Class<? extends Annotation> supportedAnnotationType() {
    return GameDataCommand.Register.class;
  }

  @Override
  protected void processElement(Context ctx) throws IOException {
    ProcType procType = ctx.getProcessingType();
    if (!isCommand(procType)) {
      procType.getLogger().warn("忽略：" + procType);
      return;
    }

    procType.getPackage().writeToFile(TypeSpec
        .classBuilder(getCommandName(procType) + "LoadMeta")
        .addModifiers(Modifier.FINAL)
        .addAnnotation(Component.class)
        .superclass(getHolderClass(procType))
        .build());

    new LoadResultImplGenerator().generate();
  }

  private boolean isCommand(ProcType procType) {
    return procType.toElement().getInterfaces().stream()
        .map(m -> (DeclaredType) m)
        .map(t -> (TypeElement) t.asElement())
        .map(TypeElement::getQualifiedName)
        .map(CharSequence::toString)
        .anyMatch(n -> n.equals(GameDataCommand.class.getName()));
  }

  private String getCommandName(ProcType type) {
    return type.toElement().getSimpleName().toString();
  }

  private TypeName getHolderClass(ProcType procType) {
    return ParameterizedTypeName.get(
        ClassName.get(DataLoadMetaHolder.class), procType.toTypeName());
  }
}
