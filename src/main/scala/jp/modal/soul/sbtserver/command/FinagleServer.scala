package jp.modal.soul.sbtserver.command

import java.io.{BufferedReader, FileInputStream, InputStreamReader}

import com.twitter.finagle.{Http, Service}
import com.twitter.io.Charsets._
import com.twitter.util.{Await, Future}
import jp.modal.soul.sbtserver.util.Loan
import org.jboss.netty.buffer.ChannelBuffers._
import org.jboss.netty.handler.codec.http.{DefaultHttpResponse, HttpRequest, HttpResponse, HttpResponseStatus}

/**
 * Created by imae on 2015/06/11.
 */
case class FinagleServer(port:Option[Int]) {
  private[this] final val CHAR_SET = "UTF-8"
  private[this] def requestLog(implicit req:HttpRequest) = s"${req.getProtocolVersion} ${req.getMethod} ${req.getUri}"
  
  val service = new Service[HttpRequest, HttpResponse] {
    override def apply(request: HttpRequest): Future[HttpResponse] = {
      implicit val req = request
      println(requestLog)
      val uri = s".${request.getUri}"
      val builder = new StringBuilder
      for {
        in <- Loan(new FileInputStream(uri))
        reader <- Loan(new InputStreamReader(in, CHAR_SET))
        buffer <- Loan(new BufferedReader(reader))
      } {
        var line = buffer.readLine()
        while(line != null) {
          builder.append(line)
          line = buffer.readLine()
        }
      }
      val response = new DefaultHttpResponse(request.getProtocolVersion, 
        if(builder.isEmpty) HttpResponseStatus.NOT_FOUND else HttpResponseStatus.OK)
      response.setContent(copiedBuffer(builder.toString(), Utf8))
      Future(response)
    }
  }

  Await.ready(Http.serve(s":${port.getOrElse(9000)}", service))
}