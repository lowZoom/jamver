package luj.game.server.maven.plugin

import groovy.transform.CompileStatic
import luj.game.server.api.net.NetProto
import luj.proto.maven.plugin.generate.ProtoAllGenerator
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugins.annotations.LifecyclePhase
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import org.apache.maven.project.MavenProject

@CompileStatic
@Mojo(name = 'protoc', defaultPhase = LifecyclePhase.GENERATE_SOURCES)
class ProtocMojo extends AbstractMojo {

  @Override
  void execute() {
    ProtoAllGenerator.Factory
        .create(NetProto, _project, _protoc, getLog())
        .generate()
  }

  @Parameter(defaultValue = '${project}', readonly = true)
  private MavenProject _project

  @Parameter(property = 'jamver.bin.protoc')
  private File _protoc
}
