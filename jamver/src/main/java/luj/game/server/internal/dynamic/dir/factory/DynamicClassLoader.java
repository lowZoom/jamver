package luj.game.server.internal.dynamic.dir.factory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DynamicClassLoader extends ClassLoader {

  DynamicClassLoader(ClassLoader defaultLoader, Path classDir) {
    _classDir = classDir;
    _defaultLoader = defaultLoader;
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    String classFile = name.replace(".", "/") + ".class";
    Path classPath = _classDir.resolve(classFile);

    if (!Files.isRegularFile(classPath)) {
      return _defaultLoader.loadClass(name);
    }

    try {
      byte[] classBytes = Files.readAllBytes(classPath);
      return defineClass(name, classBytes, 0, classBytes.length);

    } catch (IOException e) {
      throw new ClassNotFoundException(name, e);
    }
  }

  private final Path _classDir;

  private final ClassLoader _defaultLoader;
}
