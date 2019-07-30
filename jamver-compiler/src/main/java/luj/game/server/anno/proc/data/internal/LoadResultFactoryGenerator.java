package luj.game.server.anno.proc.data.internal;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import java.io.IOException;
import luj.game.server.internal.data.load.DataLoadMetaHolder;
import luj.generate.annotation.process.type.ProcType;

public class LoadResultFactoryGenerator {

  public LoadResultFactoryGenerator(ProcType commandType, String commandName) {
    _commandType = commandType;
    _commandName = commandName;
  }

  public void generate() throws IOException {
    new BeanInSamePackageGenerator(_commandType, _commandName + "LoadResultFac").generate(b -> b
        .addSuperinterface(getFactoryClass())
    );
  }

  private TypeName getFactoryClass() {
    return ParameterizedTypeName.get(ClassName
        .get(DataLoadMetaHolder.class), _commandType.toTypeName());
  }

  private final ProcType _commandType;
  private final String _commandName;
}
