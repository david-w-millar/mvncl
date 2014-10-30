package millarts.mvncl.domain

import groovy.transform.ToString

/** Maven central solr query header info */
@ToString
class ResponseHeader {
  final Integer QTime
  final Integer status
  final Map<String,String> params

  Map<String,String> getParams() {
    params.asImmutable()
  }
}

