package millarts.mvncl

import static groovyx.net.http.ContentType.*
import static groovy.json.JsonOutput.*

import groovyx.net.http.RESTClient
import groovyx.net.http.HttpResponseDecorator as Response

import millarts.mvncl.domain.*

/**
 * TODO: Replacei (awesome) asserts with guava Preconditions
 *
 * Basic maven central api client
 */
class McClient {

  // Solr
  private static final String SOLR_SELECT = 'solrsearch/select'
  private static final String WT = 'json'
  private static final Integer DEFAULT_ROWS = 20

  // Client
  static final RESTClient client = new RESTClient('http://search.maven.org/', JSON)

  /**
   * Gav search - works for most combination of group,
   * artifact, version, packaging, and mysterious 'i' argument.
   *
   * <pre>
   * // eg:
   * Response response = gavpi( [g:'com.google.inject', a:'guice'] )
   * </pre>
   */
  static SearchResults gavpi(final Map gavc) {
    assert gavc
    String q = solrQueryFromGvacMap(gavc)
    Response r = client.get( path: SOLR_SELECT, query: [q: q, wt: 'json', rows: DEFAULT_ROWS, core: 'gav'] )
    SearchResults.fromJson( toJson(r.data) )
  }

  static SearchResults gavpi(final String g, final String a, final String v, final String p, final String i) {
    assert g || a || v
    Map m = [ g: g, a: a, v: v, p: p, i: i ].findAll { k, value -> value }
    gavpi(m)
  }

  static SearchResults search(final String q, int rows = DEFAULT_ROWS) {
    assert q
    Response r = client.get( path: SOLR_SELECT, query: [ q: q, rows: rows, wt: WT] )
    SearchResults.fromJson( toJson(r.data) )
  }

  static SearchResults idsearch(final String id, int rows = DEFAULT_ROWS) {
    assert id
    Response r = client.get( path: SOLR_SELECT, query: [ q: "id:\"${id}\"", rows: rows,  wt: WT] )
    SearchResults.fromJson( toJson(r.data) )
  }

  static SearchResults fqcn(final String fqcn, Integer rows = DEFAULT_ROWS) {
    assert fqcn
    Response r = client.get( path: SOLR_SELECT, query: [ q: "fc:\"${fqcn}\"", rows: rows, wt: WT ])
    SearchResults.fromJson( toJson(r.data) )
  }

  // TODO: this, if it proves worth while
  /** eg: 'com/jolira/guice/3.0.0/guice-3.0.0.pom' */
  static Response download(final String path, boolean head = false) {
    assert path
    Map params = [ path: 'remotecontent', query: [ filepath: path] ]
    (head) ? client.head(params) : client.get(params)
  }


  // ----- [ Helpers ] -----

  private static final List GAVC_WHITELIST = ['g', 'a', 'v', 'p', 'i'].asImmutable()
  private static String solrQueryFromGvacMap(Map m) {
    m.findAll { it.key in GAVC_WHITELIST }
     .collect { "${it.key}:\"${it.value}\"" }
     .join(' AND ')
  }

}

