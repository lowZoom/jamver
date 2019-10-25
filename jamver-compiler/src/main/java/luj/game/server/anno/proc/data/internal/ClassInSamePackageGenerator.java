package luj.game.server.anno.proc.data.internal;

import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.function.Consumer;
import javax.lang.model.element.Modifier;
import luj.generate.annotation.process.type.ProcType;

public class ClassInSamePackageGenerator {

  public ClassInSamePackageGenerator(ProcType procType, String beanName) {
    _procType = procType;
    _beanName = beanName;
  }

  public TypeSpec generate(Consumer<TypeSpec.Builder> beanBuilder) {
    TypeSpec.Builder classBuilder = TypeSpec.classBuilder(_beanName)
        .addModifiers(Modifier.FINAL);

    beanBuilder.accept(classBuilder);
    TypeSpec newClass = classBuilder.build();

    try {
      _procType.getPackage().writeToFile(newClass);
      return newClass;

    } catch (IOException e) {
      throw new UnsupportedOperationException(e);
    }
  }

  private final ProcType _procType;

  private final String _beanName;
}
