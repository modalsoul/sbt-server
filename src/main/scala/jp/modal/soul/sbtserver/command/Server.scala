package jp.modal.soul.sbtserver.command

import java.io.{BufferedReader, FileInputStream, InputStreamReader}
import java.net.InetSocketAddress

import com.sun.net.httpserver._
import jp.modal.soul.sbtserver.util.Loan

/**
 * Created by imae on 2015/06/09.
 */

case class Server(port:Option[Int]) {
  private[this] final val DEFAULT_PORT = 9000
  
  val server = HttpServer.create(new InetSocketAddress(port.getOrElse(DEFAULT_PORT)), 0)
  server.createContext("/", new ServerHttpHandler)
  server.start()
}

class ServerHttpHandler extends HttpHandler {
  private[this] final val CHAR_SET = "UTF-8"
  
  override def handle(he: HttpExchange): Unit = {
    val (code, bytes) = 
      try { 
        val uri = s".${he.getRequestURI.getPath}"
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
        (200, builder.toString().getBytes(CHAR_SET))
      } catch {
        case e:Exception =>
          e.printStackTrace()
          (500, e.getMessage.getBytes(CHAR_SET))
      }
    he.sendResponseHeaders(code, bytes.length)
    he.getResponseBody.write(bytes)
  }
}