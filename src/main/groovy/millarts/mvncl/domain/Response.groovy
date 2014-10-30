package millarts.mvncl.domain

import com.fasterxml.jackson.annotation.JsonProperty

/** Maven central solr query response data */
class Response {
  final Integer numFound
  final Integer start
  private Set<Artifact> artifacts

  @JsonProperty(value='docs')
  void setArtifacts(Set<Artifact> docs) {
    // Maven Centrol omits the version property in the artifact object with the latest version
    artifacts = docs.collect { a -> a.v = a.v ?: a.latestVersion; a } as TreeSet<Artifact>
  }

  @JsonProperty(value='docs')
  List<Artifact> getArtifacts() { artifacts.asImmutable() }

  int getLastResultNumber() { artifacts.size() + start }


  // ----- [ Presentation ] -----

  @Override String toString() {
    "${pagingText}\n\n${resultsText}"
  }

  String getPagingText() {
    numFound <= 0 ?
        'No Results Found' :
        "Results: ( ${start + 1} to ${lastResultNumber} of ${numFound} ${'result'.plural(numFound)} )"
  }

  String getResultsText() {
    artifacts*.toString().collect { "  ${it}" }
      .join('\n')
  }

}

