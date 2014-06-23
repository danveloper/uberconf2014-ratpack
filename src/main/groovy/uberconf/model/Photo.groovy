package uberconf.model

import groovy.transform.Canonical
import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

@Canonical
@CompileStatic
@EqualsAndHashCode(includes = ['id'])
class Photo {
  Long id
  String name
  byte[] data

  String getBase64() {
    data.encodeBase64().toString()
  }
}
