package luj.game.server.internal.dynamic.collect;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

final class MetaReaderImpl implements MetadataReader, MetadataReaderFactory {

  @Override
  public ClassMetadata getClassMetadata() {
    return _metadata;
  }

  @Override
  public AnnotationMetadata getAnnotationMetadata() {
    return _metadata;
  }

  @Override
  public Resource getResource() {
    throw new UnsupportedOperationException("getResource");
  }

  @Override
  public MetadataReader getMetadataReader(String className) {
    throw new UnsupportedOperationException("getMetadataReader");
  }

  @Override
  public MetadataReader getMetadataReader(Resource resource) {
    throw new UnsupportedOperationException("getMetadataReader");
  }

  AnnotationMetadata _metadata;
}
