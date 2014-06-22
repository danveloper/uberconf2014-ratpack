package uberconf.services

import java.nio.file.Files
import java.nio.file.Path
import ratpack.form.UploadedFile
import uberconf.model.PhotoService


import static java.nio.file.Files.write

class DefaultPhotoService implements PhotoService {
  private static final PREFIX = "ratpack-"
  private static final SUFFIX = ".jpg"
  final Path tmpDir = File.createTempDir().toPath()

  @Override
  String save(UploadedFile f) {
    Path dest = Files.createTempFile(tmpDir, PREFIX, SUFFIX)
    write dest, f.bytes fileName
    dest.fileName.toString().replaceAll("^${PREFIX}", "").replaceAll("${SUFFIX}\$", "")
  }

  @Override
  Path get(String name) {
    tmpDir.resolve getFileName(name)
  }

  @Override
  void delete(String name) {
    def path = tmpDir.resolve getFileName(name)
    Files.delete(path)
  }

  private static String getFileName(String name) {
    "${PREFIX}${name}${SUFFIX}"
  }
}
