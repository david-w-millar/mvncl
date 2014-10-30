package millarts.mvncl.domain

/** Provides formatting for various dependency management / build automation systems. */
@SuppressWarnings('SerializableClassMustDefineSerialVersionUID')
enum Format {

  maven     ( Format.&mavenFormat ),
  buildr    ( Format.&buildrFormat ),
  ivy       ( Format.&ivyFormat ),
  grape     ( Format.&grapeFormat ),
  gradle    ( Format.&gradleFormat ),
  grails    ( Format.&gradleFormat ),
  sbt       ( Format.&sbtFormat ),
  leiningen ( Format.&leiningenFormat )

  private final Closure formatter
  Format(final Closure formatter) {
    this.formatter = formatter
  }

  String format(final Artifact a) {
    formatter.call(a)
  }

  static String mavenFormat(final Artifact a) {
    String comment = a.metadataText ? "<-- ${a.metadataText} -->\n" : ''
  """\
${comment}  <dependency>
    <groupId>${a.g}</groupId>
    <artifactId>${a.a}</artifactId>
    <version>${a.version}</version>
  </dependency>
"""
  }

  @SuppressWarnings('FactoryMethodName')
  static String buildrFormat(final Artifact a) {
    String md = a.metadataText ? "\t# ${a.metadataText}" : ''
    "${a.g}:${a.a}:${a.version}${md}"
  }

  static String ivyFormat(final Artifact a) {
    String md = a.metadataText ? "\t<!-- ${a.metadataText} -->" : ''
    "<dependency org=\"${a.g}\" name=\"${a.a}\" rev=\"${a.version}\" />${md}"
  }

  static String grapeFormat(final Artifact a) {
    String md = a.metadataText ? "\t// ${a.metadataText}" : ''
    "@Grab('${a.g}:${a.a}:${a.version}')${md}"
  }

  static String gradleFormat(final Artifact a) {
    "compile '${a.g}:${a.a}:${a.version}'\t// ${a.metadataText}"
  }

  static String sbtFormat(final Artifact a) {
    "libraryDependencies += '${a.g}' % '${a.a}' % '${a.version}'\t// ${a.metadataText}"
  }

  static String leiningenFormat(final Artifact a) {
    "[${a.g}/${a.a} '${a.version}']\t ; ${a.metadataText}"
  }

}

