package uberconf.model

import com.jayway.restassured.response.Header
import com.jayway.restassured.specification.RequestSpecification
import ratpack.groovy.test.*
import ratpack.test.remote.RemoteControl
import spock.lang.Specification

class FunctionalSpec extends Specification {

  LocalScriptApplicationUnderTest aut = new LocalScriptApplicationUnderTest('other.remoteControl.enabled': 'true')
  @Delegate TestHttpClient testHttpClient = TestHttpClients.testHttpClient(aut, { RequestSpecification spec ->
    spec.header(new Header("Accept", "text/html"))
  })
  RemoteControl remoteControl = new RemoteControl(aut)

  void "Titles for Photos should display properly"() {
    setup:
      remoteControl.exec {
        get(PhotoDB).save("Foo.JPEG", [1, 2, 3] as byte[])
      }

    when:
      def resp = get("show/1").andReturn()

    then:
      resp.statusCode == 200
      resp.body.asInputStream().text.contains("<h1>Foo.JPEG</h1>")
  }
}
