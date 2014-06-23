import ratpack.form.Form
import ratpack.groovy.sql.SqlModule
import ratpack.hikari.HikariModule
import ratpack.launch.LaunchConfig
import ratpack.remote.RemoteControlModule
import uberconf.model.*


import static ratpack.groovy.Groovy.groovyTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    add new HikariModule([URL: "jdbc:h2:mem:dev;INIT=CREATE SCHEMA IF NOT EXISTS DEV"], "org.h2.jdbcx.JdbcDataSource")
    add new SqlModule()
    add new PhotoModule()
    add new RemoteControlModule()

    init { PhotoDB photoDB ->
      photoDB.createTable()
    }
  }

  handlers {
    prefix("photo") {
      post { PhotoService photoService ->
        def form = parse(Form)
        def file = form.file("photo")
        blocking {
          def photo = new Photo(null, file.fileName, file.bytes)
          photoService.save(photo)
        } then { Photo photo ->
          redirect "/show/${photo.id}"
        }
      }
      delete("delete/:id") { PhotoService photoService ->
        blocking {
          photoService.get(pathTokens.asLong("id"))
        } onError {
          response.status 500
          response.send()
        } then { Photo photo ->
          if (photo) {
            photoService.delete(photo)
            response.status 204
          } else {
            response.status 404
          }
          response.send()
        }
      }
    }

    get("show/:id") { PhotoService photoService ->
      blocking {
        photoService.get(pathTokens.asLong("id"))
      } then { Photo photo ->
        render photo
      }
    }

    get { PhotoService photoService, LaunchConfig launchConfig ->
      render groovyTemplate("landing.html", photos: photoService.all(), client: launchConfig.getOther("client", "default"))
    }

    assets "public"
  }
}
