package jp.modal.soul.sbtserver.command.server

import com.twitter.finagle.Http
import com.twitter.util.Await

/**
 * Created by imae on 2015/06/11.
 */
case class FinagleServer(assignedPort:Option[Int]) extends RequestLog {
  private[this] final val DEFAULT_PORT = 9000
  val port = s":${assignedPort.getOrElse(DEFAULT_PORT).toString}"
  Await.ready(Http.serve(port, new FinagleService))
}