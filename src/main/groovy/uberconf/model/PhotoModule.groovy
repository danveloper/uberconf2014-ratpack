package uberconf.model

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import groovy.transform.CompileStatic
import uberconf.renderers.PhotoRenderer
import uberconf.services.DefaultPhotoService

@CompileStatic
class PhotoModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(PhotoDB).in(Scopes.SINGLETON)
    bind(PhotoRenderer).in(Scopes.SINGLETON)
    bind(PhotoService).to(DefaultPhotoService).in(Scopes.SINGLETON)
  }
}
