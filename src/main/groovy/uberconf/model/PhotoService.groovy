package uberconf.model

import java.nio.file.Path
import ratpack.form.UploadedFile

/**
 * An interface for working with uploaded Photos
 */
public interface PhotoService {

  /**
   * Gets all the photos
   *
   * @return a set of all the photos
   */
  Set<Photo> all()

  /**
   * Saves the file and returns the file name
   *
   * @param f
   * @return the photo object w/ id
   */
  Photo save(Photo photo)

  /**
   * Retrieves a photo by id
   *
   * @param id
   * @return the photo
   */
  Photo get(Long id)

  /**
   * Deletes a photo
   */
  void delete(Photo photo)
}
