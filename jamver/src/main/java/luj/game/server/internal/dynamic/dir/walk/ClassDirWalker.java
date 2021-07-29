package luj.game.server.internal.dynamic.dir.walk;

import com.google.common.collect.ImmutableList;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import luj.ava.file.path.PathX;
import luj.game.server.internal.dynamic.dir.ClassDir;

public enum ClassDirWalker {
  GET;

  public List<ClassDir.Class> walk(PathX path, ClassLoader loader) {
    Path classDir = path.asPath();
    return path.walk(s -> s
        .filter(PathX::isRegularFile)
        .filter(p -> p.getFileName().endsWith(".class"))
        .map(PathX::asPath)
        .map(p -> toClassName(classDir, p))
        .map(n -> makeClass(n, loader))
        .collect(Collectors.toList()));
  }

  private String toClassName(Path dir, Path clazz) {
    return ImmutableList.copyOf(dir.relativize(clazz)).stream()
        .map(Path::toString)
        .collect(Collectors.joining("."))
        .replaceAll("\\.class$", "");
  }

  private ClassImpl makeClass(String name, ClassLoader loader) {
    ClassImpl clazz = new ClassImpl();
    clazz._name = name;
    clazz._classLoader = loader;
    return clazz;
  }
}
