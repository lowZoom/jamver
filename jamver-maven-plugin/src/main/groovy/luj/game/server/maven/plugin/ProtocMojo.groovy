package luj.game.server.maven.plugin

import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugin.MojoExecutionException
import org.apache.maven.plugin.MojoFailureException
import org.apache.maven.plugins.annotations.LifecyclePhase
import org.apache.maven.plugins.annotations.Mojo

@Mojo(name = 'protoc', defaultPhase = LifecyclePhase.GENERATE_SOURCES)
class ProtocMojo extends AbstractMojo {

//  @Parameter(defaultValue = '${project}', readonly = true)
//  private MavenProject _project

  @Override
  void execute() throws MojoExecutionException, MojoFailureException {
    println('测测测测测测测测测测测测测测测测测测测测测测测测测测测测测测试')
    println('测测测测测测测测测测测测测测测测测测测测测测测测测测测测测测试')
    println('测测测测测测测测测测测测测测测测测测测测测测测测测测测测测测试')
    println('测测测测测测测测测测测测测测测测测测测测测测测测测测测测测测试')
    println('测测测测测测测测测测测测测测测测测测测测测测测测测测测测测测试')
    println('测测测测测测测测测测测测测测测测测测测测测测测测测测测测测测试')
  }
}
