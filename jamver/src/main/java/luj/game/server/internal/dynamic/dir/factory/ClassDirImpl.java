package luj.game.server.internal.dynamic.dir.factory;

import java.util.stream.Stream;
import luj.ava.file.path.PathX;
import luj.game.server.internal.dynamic.dir.ClassDir;
import luj.game.server.internal.dynamic.dir.walk.ClassDirWalker;

final class ClassDirImpl implements ClassDir {

  @Override
  public ClassLoader getLoader() {
    return _classLoader;
  }

  @Override
  public Stream<Class> walk() {
    return ClassDirWalker.GET.walk(_path, _classLoader).stream();
  }

  ClassLoader _classLoader;

  PathX _path;
}
