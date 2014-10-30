package millarts.mvncl

import static millarts.mvncl.McClient.*

import org.junit.*
import spock.lang.*

import groovyx.net.http.HttpResponseDecorator as Response

import co.freeside.betamax.*
import co.freeside.betamax.tape.yaml.OrderedPropertyComparator
import co.freeside.betamax.tape.yaml.TapePropertyUtils
import co.freeside.betamax.httpclient.BetamaxRoutePlanner
import org.yaml.snakeyaml.introspector.Property

import millarts.mvncl.domain.*

class McClientSpec extends Specification {

  @Rule Recorder recorder = new Recorder()

  void setupSpec() {
    BetamaxRoutePlanner.configure(client.client)
    TapePropertyUtils.metaClass.sort = { Set<Property> properties, List<String> names ->
      new LinkedHashSet(properties.sort( true, new OrderedPropertyComparator(names)))
    }
  }

  @Betamax(tape='mvncl')
  void 'I can search for artifacts' () {
    when: SearchResults r = search('guice')
    then: r
  }


  @Betamax(tape='mvncl')
  @Unroll
  //gavpi(Map gavc) {
  void "I can perform a gav searches with #m" () {
    when: SearchResults r = gavpi(m)
    then: r

    where:
    m <<  [
      [ g: 'com.google.inject' ],
      [ g: 'com.google.inject', a: 'guice' ],
      [ g: 'com.google.inject', a: 'guice', v: '3.0' ],
      [ a: 'guice' ],
      [ a: 'guice', v: '3.0' ],
      [ g: 'com.google.inject', v: '3.0' ]
    ]
  }


  void 'I can download artifacts given a path' () {
    when: Response r = download('com/jolira/guice/3.0.0/guice-3.0.0.pom', true)
    then: r.isSuccess()
  }

}

