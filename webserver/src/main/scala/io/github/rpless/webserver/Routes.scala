package io.github.rpless.webserver

import akka.http.scaladsl.server.Directives._

object Routes {
  def apply() =
    path("")(getFromResource("web/index.html")) ~
    path("browser-fastopt.js")(getFromResource("browser-fastopt.js")) ~
    getFromResourceDirectory("web")
}
