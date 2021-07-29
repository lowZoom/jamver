package luj.game.server.internal.dynamic.dir.walk;

import luj.game.server.internal.dynamic.dir.ClassDir;

final class ClassImpl implements ClassDir.Class {

  @Override
  public String getName() {
    return _name;
  }

  @Override
  public Class<?> load() {
    try {
      return Class.forName(_name, true, _classLoader);

    } catch (ClassNotFoundException e) {
      throw new UnsupportedOperationException(e);
    }
  }

  String _name;

  ClassLoader _classLoader;
}
