@Grab("io.ratpack:ratpack-groovy:0.9.5")
import ratpack.exec.ExecControl
import ratpack.form.*
import static ratpack.groovy.Groovy.ratpack

ratpack {
  handlers {
    handler {
      byMethod {
        post { 
          def form = parse(Form)
          def file = File.createTempFile("ratpack", ".jpg") << form.file("f").bytes
          response.sendFile context, file.toPath()
        }
        get {
          response.send "text/html",  """\
                                      |<!DOCTYPE html>
                                      |<html>
                                      |<head>
                                      |</head>
                                      |<body>
                                      |  <h1>Send me a file!</h1>
                                      |  <form method="POST" enctype="multipart/form-data">
                                      |    <input name="f" type="file">
                                      |    <input type="submit">
                                      |  </form>
                                      |</body>
                                      |</html>
                                      """.stripMargin()
        }
      }
    }
  }
}
