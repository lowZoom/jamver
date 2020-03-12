package luj.game.server.anno.proc.data;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Optional;
import javax.annotation.processing.Processor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import luj.game.server.anno.proc.data.internal.LoadResultFactoryGenerator;
import luj.game.server.anno.proc.data.internal.LoadResultImplGenerator;
import luj.game.server.api.data.GameDataCommand;
import luj.game.server.internal.data.load.meta.DataLoadMetaHolder;
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
    Optional<DeclaredType> cmdDeclar = findCommandDeclaration(procType);

    if (!cmdDeclar.isPresent()) {
      procType.getLogger().warn("忽略：" + procType);
      return;
    }

    String cmdName = getCommandName(procType);
    procType.getPackage().writeToFile(TypeSpec
        .classBuilder(cmdName + "LoadMeta")
        .addModifiers(Modifier.FINAL)
        .addAnnotation(Component.class)
        .superclass(getHolderClass(procType))
        .build());

    new LoadResultImplGenerator(procType, cmdName, cmdDeclar.get()).generate()
        .map(n -> new LoadResultFactoryGenerator(procType, cmdName, n))
        .ifPresent(LoadResultFactoryGenerator::generate);
  }

  private Optional<DeclaredType> findCommandDeclaration(ProcType procType) {
    return procType.toElement().getInterfaces().stream()
        .map(m -> (DeclaredType) m)
        .filter(this::isCommandType)
        .findAny();
  }

  private String getCommandName(ProcType type) {
    return type.toElement().getSimpleName().toString();
  }

  private boolean isCommandType(DeclaredType type) {
    return ((TypeElement) type.asElement())
        .getQualifiedName()
        .contentEquals(GameDataCommand.class.getName());
  }

  private TypeName getHolderClass(ProcType procType) {
    return ParameterizedTypeName.get(
        ClassName.get(DataLoadMetaHolder.class), procType.toTypeName());
  }
}
