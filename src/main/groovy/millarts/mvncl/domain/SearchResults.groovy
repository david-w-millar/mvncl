package millarts.mvncl.domain

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.*


/** Composite maven central query results */
class SearchResults {

  // Response
  final ResponseHeader responseHeader
  final Response response

  // Marshalling
  private static ObjectMapper mapper

  static {
    mapper = new ObjectMapper()
      .setSerializationInclusion(JsonInclude.Include.NON_NULL)
      .configure(SerializationFeature.INDENT_OUTPUT, true)
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  }

  static SearchResults fromJson(String json) {
    mapper.readValue(json, SearchResults)
  }

  @Override String toString() { response.toString() }

}

