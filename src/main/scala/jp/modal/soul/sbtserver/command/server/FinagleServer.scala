package jp.modal.soul.sbtserver.command.server

import com.twitter.finagle.Http
import com.twitter.util.Await

/**
 * Created by imae on 2015/06/11.
 */
case class FinagleServer(assignedPort:Option[Int], assignedBaseDir:Option[String]) extends ServerConfig with RequestLog {
  val service = new FinagleService(path)
  Await.ready(Http.serve(port, service))
}