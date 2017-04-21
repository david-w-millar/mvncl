package millarts.mvncl

import static millarts.mvncl.McClient.*

import millarts.mvncl.domain.*
import io.airlift.command.*

/** Cli for working with maven central */
@SuppressWarnings('Println')
abstract class MvnCli implements Runnable {

  @Option(
    type = OptionType.GLOBAL,
    name = ['-f', '--format'],
    title = 'format',
    description = 'Dependency Format (maven|buildr|ivy|grape|gradle|grails|sbt|leiningen)',
    allowedValues = [ 'maven', 'buildr', 'ivy', 'grape', 'gradle', 'grails', 'sbt', 'leiningen' ]
  )
  Format format = Format.gradle

  @SuppressWarnings('SystemErrPrint')
  static void main(String[] args) {
    Cli<Runnable> cliParser = parser
    try {
      cliParser.parse(args).run()
    } catch (ParseException e) {
      System.err.println("\nError: ${e.message}\n" )
      cliParser.parse(['help']).run()
    }
  }

  private static Cli<Runnable> getParser() {
    Cli.CliBuilder<Runnable> builder = Cli.<Runnable>builder('mvncl')
      .withDescription('Maven repo command line tools')
      .withDefaultCommand(Help)
      .withCommands(Help)

    builder.withGroup('search')
      .withDescription('Search Maven Central for Artifacts')
      .withDefaultCommand(MainSearch)
      .withCommands(MainSearch, GavpSearch, IdSearch, FqcnSearch)
    builder.build()
  }

  @Override void run() {
    Artifact.format = format
    go()
  }

  abstract void go()

  @Command( name = 'main', description = 'maven central main search')
  static class MainSearch extends MvnCli {
    @Option(name = '-r', description = 'max number of results') int rows = 20
    @Arguments(required = true, description = 'query string') String q
    @Override void go() { println search(q, rows) }
  }

  @Command(name = 'gavp', description = 'search by a combination of group, artifact, version, and package')
  static class GavpSearch extends MvnCli {
    @Option(name = '-g', description = 'group')         String g
    @Option(name = '-a', description = 'artifact name') String a
    @Option(name = '-v', description = 'version')       String v
    @Option(name = '-p', description = 'packaging')     String p
    @Option(name = '-i', description = '???')           String i
    @Override void go() { println gavpi(g, a, v, p, i) }
  }

  @Command(name = 'id', description = 'search by id (group and name)')
  static class IdSearch extends MvnCli {
    @Arguments(required = true, description = 'id (gradle style eg: "org.codehaus.groovy:groovy-all")') String id
    @Override void go() { println idsearch(id) }
  }

  @Command(name = 'fqcn', description = 'search by fully qualified class name')
  static class FqcnSearch extends MvnCli {
    @Arguments(required = true, description = 'fully-qualified class name') String fqcn
    @Override void go() { println fqcn(fqcn) }
  }

}

