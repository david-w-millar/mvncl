package millarts.mvncl.domain

import groovy.transform.Sortable

@Sortable( includes=[ 'g', 'a', 'v', 'p' ] )
class Artifact {

  // Dependency Format - todo - this could be more clean
  static Format format = Format.gradle

  // Coordinates
  final String g
  final String a
  String v
  final String p

  // Repo
  final String repositoryId

  // Identity
  final String id
  int versionCount
  final String latestVersion = null
  final Long timestamp

  // Metadata
  final List<String> ec
  final List<String> text

  List<String> getEc()   { ec.asImmutable() }
  List<String> getText() { text.asImmutable() }

  String getVersion() { v ?: latestVersion }


  // ----- [ Presentation ] -----

  @Override String toString() { format.format(this) }

  String getMetadataText() {
    hasMetadata() ? "(${versionCount} ${'version'.plural(versionCount)}, latest: ${latestVersion})" : null
  }

  private boolean hasMetadata() {
    latestVersion && versionCount >= 0
  }

}

