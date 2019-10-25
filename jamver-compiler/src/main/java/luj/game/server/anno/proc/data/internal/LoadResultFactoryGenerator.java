package luj.game.server.anno.proc.data.internal;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import java.lang.reflect.Method;
import javax.lang.model.element.Modifier;
import luj.game.server.internal.data.load.generate.DataLoadResultFactory;
import luj.generate.annotation.process.type.ProcType;
import org.springframework.stereotype.Component;

public class LoadResultFactoryGenerator {

  public LoadResultFactoryGenerator(ProcType commandType, String commandName,
      String resultImplName) {
    _commandType = commandType;
    _commandName = commandName;

    _resultImplName = resultImplName;
  }

  public void generate() {
    new ClassInSamePackageGenerator(_commandType, _commandName + "LoadResultFac")
        .generate(this::initFactoryClass);
  }

  private void initFactoryClass(TypeSpec.Builder clazz) {
    clazz.addAnnotation(Component.class);

    clazz.addSuperinterface(ParameterizedTypeName.get(ClassName
        .get(DataLoadResultFactory.class), _commandType.toTypeName()));

    clazz.addMethod(override("create")
        .addStatement("return new $L()", _resultImplName)
        .build());
  }

  private MethodSpec.Builder override(String name) {
    Method method = getMethod(name);

    return MethodSpec.methodBuilder(method.getName())
        .addAnnotation(Override.class)
        .addModifiers(Modifier.PUBLIC)
        .returns(method.getReturnType());
  }

  private Method getMethod(String name) {
    try {
      return DataLoadResultFactory.class.getMethod(name);

    } catch (NoSuchMethodException e) {
      throw new UnsupportedOperationException(e);
    }
  }

  private final ProcType _commandType;
  private final String _commandName;

  private final String _resultImplName;
}
