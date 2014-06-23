package uberconf.services

import com.google.inject.Inject
import groovy.sql.GroovyRowResult
import groovy.transform.CompileStatic
import org.h2.jdbc.JdbcBlob
import uberconf.model.*

@CompileStatic
class DefaultPhotoService implements PhotoService {
  private final PhotoDB photoDB

  @Inject
  DefaultPhotoService(PhotoDB photoDB) {
    this.photoDB = photoDB
  }

  @Override
  Set<Photo> all() {
    photoDB.all().collect {
      map it
    } as Set
  }

  @Override
  Photo save(Photo photo) {
    Long id = photoDB.save(photo.name, photo.data)
    photo.id = id
    photo
  }

  @Override
  Photo get(Long id) {
    map photoDB.findOne(id)
  }

  @Override
  void delete(Photo photo) {
    photoDB.remove(photo.id)
  }

  private static Photo map(GroovyRowResult row) {
    new Photo(row.ID as Long, row.NAME as String, ((JdbcBlob)row.DATA).binaryStream.bytes)
  }
}
