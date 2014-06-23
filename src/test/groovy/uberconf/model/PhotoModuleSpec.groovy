package uberconf.model

import spock.lang.Specification
import uberconf.services.DefaultPhotoService


import static ratpack.groovy.test.TestHttpClients.testHttpClient
import static ratpack.groovy.test.embed.EmbeddedApplications.embeddedApp

class PhotoModuleSpec extends Specification {

  void "PhotoService should call to PhotoDB to save data"() {
    setup:
      def photoDB = Mock(PhotoDB)
      def photo = new Photo(name: "foo", data: [] as byte[])
      def myapp = embeddedApp {
        bindings {
          bind PhotoService, new DefaultPhotoService(photoDB)
        }
        handlers {
          post { PhotoService photoService ->
            photoService.save(photo)
            response.send()
          }
        }
      }
      myapp.server.start()

    when:
      testHttpClient(myapp).post()

    then:
      1 * photoDB.save(photo.name, _)
  }
}
