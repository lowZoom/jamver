package luj.game.server.anno.proc.data.internal;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import luj.generate.annotation.process.AnnoProc;
import luj.generate.annotation.process.type.ProcType;

public class LoadResultImplGenerator {

  public LoadResultImplGenerator(ProcType commandType,
      String commandName, DeclaredType commandDeclaration) {
    _commandType = commandType;
    _commandName = commandName;
    _commandDeclaration = commandDeclaration;
  }

  public void generate(AnnoProc.Log log) throws IOException {
    DeclaredType resultType = getResultType();
    if (isVoid(resultType)) {
      return;
    }

    new BeanInSamePackageGenerator(_commandType, _commandName + "LoadResultImpl")
        .generate(b -> initResultImpl(b, resultType, log));
  }

  private boolean isVoid(DeclaredType resultType) {
    Name name = ((TypeElement) resultType.asElement()).getQualifiedName();
    return name.contentEquals(Void.class.getName());
  }

  private DeclaredType getResultType() {
    return (DeclaredType) _commandDeclaration.getTypeArguments().get(1);
  }

  private void initResultImpl(TypeSpec.Builder clazz, DeclaredType resultType, AnnoProc.Log log) {
    clazz.addSuperinterface(TypeName.get(resultType));

    List<ResultField> fieldList = resultType.asElement()
        .getEnclosedElements().stream()
        .map(ExecutableElement.class::cast)
        .map(this::createResultField)
        .collect(Collectors.toList());

    clazz.addMethods(fieldList.stream()
        .map(this::override)
        .collect(Collectors.toList()));

    clazz.addFields(fieldList.stream()
        .map(this::makeImplField)
        .collect(Collectors.toList()));
  }

  private FieldSpec makeImplField(ResultField field) {
    TypeName returnType = TypeName.get(field._elem.getReturnType());
    return FieldSpec.builder(returnType, field._fieldName).build();
  }

  private MethodSpec override(ResultField field) {
    return MethodSpec.overriding(field._elem)
        .addStatement("return $L", field._fieldName)
        .build();
  }

  private ResultField createResultField(ExecutableElement elem) {
    ResultField field = new ResultField();
    field._elem = elem;
    field._fieldName = "_" + elem.getSimpleName();
    return field;
  }

  static class ResultField {

    ExecutableElement _elem;

    String _fieldName;
  }

  private final ProcType _commandType;

  private final String _commandName;
  private final DeclaredType _commandDeclaration;
}
