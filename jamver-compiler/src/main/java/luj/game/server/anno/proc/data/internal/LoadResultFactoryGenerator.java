package luj.game.server.anno.proc.data.internal;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import luj.game.server.internal.data.load.generate.DataLoadResultFactory;
import luj.generate.annotation.process.type.ProcType;
import org.springframework.stereotype.Component;

public class LoadResultFactoryGenerator {

  public LoadResultFactoryGenerator(ProcType commandType, String commandName) {
    _commandType = commandType;
    _commandName = commandName;
  }

  public void generate() throws IOException {
    new BeanInSamePackageGenerator(_commandType, _commandName + "LoadResultFac")
        .generate(this::initFactoryClass);
  }

  private void initFactoryClass(TypeSpec.Builder clazz) {
    clazz.addAnnotation(Component.class);

    clazz.addSuperinterface(ParameterizedTypeName.get(ClassName
        .get(DataLoadResultFactory.class), _commandType.toTypeName()));
  }

  private final ProcType _commandType;

  private final String _commandName;
}
