package uberconf.renderers

import com.google.inject.Inject
import groovy.transform.CompileStatic
import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.render.GroovyRendererSupport
import ratpack.groovy.templating.internal.DefaultTemplate
import ratpack.launch.LaunchConfig
import uberconf.model.Photo


import static groovy.json.JsonOutput.toJson

@CompileStatic
class PhotoRenderer extends GroovyRendererSupport<Photo> {
  private final LaunchConfig launchConfig

  @Inject
  PhotoRenderer(LaunchConfig launchConfig) {
    this.launchConfig = launchConfig
  }

  @Override
  void render(GroovyContext context, Photo photo) throws Exception {
    context.byContent {
      json {
        render toJson([id: photo.id, name: photo.name, resourceUri: "/show/${photo.id}".toString()])
      }
      html {
        render new DefaultTemplate("photo.html", [photo: photo,
                                                  client: launchConfig.getOther("client", "default")], null)
      }
    }
  }
}
