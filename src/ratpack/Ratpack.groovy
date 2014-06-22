import ratpack.form.Form
import uberconf.model.PhotoService
import uberconf.services.DefaultPhotoService


import static ratpack.groovy.Groovy.groovyTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    bind PhotoService, new DefaultPhotoService()
  }

  handlers {
    prefix("photo") {
      post { PhotoService photoService ->
        def form = parse(Form)
        def name = photoService.save(form.file("photo"))
        redirect "/show/$name"
      }
      get(":name") { PhotoService photoService ->
        response.sendFile context, photoService.get(pathTokens.name)
      }
      delete("delete/:name") { PhotoService photoService ->
        blocking {
          photoService.delete(pathTokens.name)
        } onError {
          response.status 500
          response.send()
        } then {
          response.status 204
          response.send()
        }
      }
    }

    get("show/:name") {
      render groovyTemplate("photo.html", name: pathTokens.name)
    }

    assets "public"
  }
}
