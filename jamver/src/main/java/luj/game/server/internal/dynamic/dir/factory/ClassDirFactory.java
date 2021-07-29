package luj.game.server.internal.dynamic.dir.factory;

import java.nio.file.Path;
import luj.ava.file.path.PathX;
import luj.game.server.internal.dynamic.dir.ClassDir;

public enum ClassDirFactory {
  GET;

  public ClassDir create(Path path) {
    ClassDirImpl dir = new ClassDirImpl();
    dir._classLoader = new DynamicClassLoader(getClass().getClassLoader(), path);
    dir._path = PathX.of(path);
    return dir;
  }
}
