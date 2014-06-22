package uberconf.model

import java.nio.file.Path
import ratpack.form.UploadedFile

/**
 * An interface for working with uploaded Photos
 */
public interface PhotoService {

  /**
   * Saves the file and returns the file name
   *
   * @param f
   * @return the file name
   */
  String save(UploadedFile f)

  /**
   * Retrieves a file by name
   *
   * @param name
   * @return the file
   */
  Path get(String name)

  /**
   * Deletes a named file
   */
  void delete(String name)
}
