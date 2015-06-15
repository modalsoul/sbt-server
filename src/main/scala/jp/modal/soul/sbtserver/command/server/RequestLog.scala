package jp.modal.soul.sbtserver.command.server

import org.jboss.netty.handler.codec.http.HttpRequest

/**
 * Created by imae on 2015/06/14.
 */
trait RequestLog {
  private[this] val requestLog = (req:HttpRequest) => s"${req.getProtocolVersion} ${req.getMethod} ${req.getUri}"
  def trace(request:HttpRequest) = println(requestLog(request))
}
