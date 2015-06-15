package jp.modal.soul.sbtserver.command.server

import com.twitter.finagle.Service
import com.twitter.io.Charsets._
import com.twitter.util.Future
import jp.modal.soul.sbtserver.ReadFile
import org.jboss.netty.buffer.ChannelBuffers._
import org.jboss.netty.handler.codec.http.{DefaultHttpResponse, HttpRequest, HttpResponse, HttpResponseStatus}

/**
 * Created by imae on 2015/06/15.
 */
class FinagleService(path:String => String) extends Service[HttpRequest, HttpResponse] with RequestLog {
  override def apply(request:HttpRequest):Future[HttpResponse] = {
    trace(request)
    val src = ReadFile.src(path(request.getUri))
    val response = new DefaultHttpResponse(request.getProtocolVersion,
      if(src.isEmpty) HttpResponseStatus.NOT_FOUND else HttpResponseStatus.OK)
    response.setContent(copiedBuffer(src, Utf8))
    Future(response)
  }
}
