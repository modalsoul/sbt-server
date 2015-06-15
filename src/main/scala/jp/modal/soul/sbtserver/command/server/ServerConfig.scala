package jp.modal.soul.sbtserver.command.server

/**
 * Created by imae on 2015/06/14.
 */
trait ServerConfig {
  val assignedPort:Option[Int]
  val assignedBaseDir:Option[String]
  private[this] final val DEFAULT_PORT = 9000
  private[this] final val DEFAULT_BASE_DIR = "."
  val port = s":${assignedPort.getOrElse(DEFAULT_PORT).toString}"
  val path = (target:String) => s"${assignedBaseDir.getOrElse(DEFAULT_BASE_DIR)}$target"
}
