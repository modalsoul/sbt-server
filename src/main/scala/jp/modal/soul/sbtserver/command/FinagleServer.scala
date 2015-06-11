package jp.modal.soul.sbtserver.command

import com.twitter.finagle.{Http, Service}
import com.twitter.io.Charsets._
import com.twitter.util.{Await, Future}
import jp.modal.soul.sbtserver.ReadFile
import org.jboss.netty.buffer.ChannelBuffers._
import org.jboss.netty.handler.codec.http.{DefaultHttpResponse, HttpRequest, HttpResponse, HttpResponseStatus}

/**
 * Created by imae on 2015/06/11.
 */
case class FinagleServer(port:Option[Int]) {
  private[this] def requestLog(implicit req:HttpRequest) = s"${req.getProtocolVersion} ${req.getMethod} ${req.getUri}"
  
  val service = new Service[HttpRequest, HttpResponse] {
    override def apply(request: HttpRequest): Future[HttpResponse] = {
      implicit val req = request
      println(requestLog)
      val src = ReadFile.src(s".${request.getUri}")
      val response = new DefaultHttpResponse(request.getProtocolVersion, 
        if(src.isEmpty) HttpResponseStatus.NOT_FOUND else HttpResponseStatus.OK)
      response.setContent(copiedBuffer(src, Utf8))
      Future(response)
    }
  }

  Await.ready(Http.serve(s":${port.getOrElse(9000)}", service))
}