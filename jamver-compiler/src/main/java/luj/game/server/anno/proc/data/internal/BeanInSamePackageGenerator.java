package luj.game.server.anno.proc.data.internal;

import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.function.Consumer;
import javax.lang.model.element.Modifier;
import luj.generate.annotation.process.type.ProcType;

public class BeanInSamePackageGenerator {

  public BeanInSamePackageGenerator(ProcType procType, String beanName) {
    _procType = procType;
    _beanName = beanName;
  }

  public void generate(Consumer<TypeSpec.Builder> beanBuilder) throws IOException {
    TypeSpec.Builder bean = TypeSpec.classBuilder(_beanName)
        .addModifiers(Modifier.FINAL);

    beanBuilder.accept(bean);
    _procType.getPackage().writeToFile(bean.build());
  }

  private final ProcType _procType;

  private final String _beanName;
}
