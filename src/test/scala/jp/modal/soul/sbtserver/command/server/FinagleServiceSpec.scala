package jp.modal.soul.sbtserver.command.server

import com.twitter.io.Charsets._
import jp.modal.soul.sbtserver.Resource
import jp.modal.soul.sbtserver.model.entity.Mock
import org.jboss.netty.buffer.ChannelBuffer
import org.jboss.netty.buffer.ChannelBuffers._
import org.jboss.netty.handler.codec.http._
import org.scalatest.{Matchers, FunSpec}
import jp.modal.soul.sbtserver.util._

/**
 * Created by imae on 2015/06/22.
 */
class FinagleServiceSpec extends FunSpec with Matchers {
  val requestBuilder = (uri:String) =>  new HttpRequest {
    override def setUri(uri: String): Unit = ???

    override def setMethod(method: HttpMethod): Unit = ???

    override def getMethod: HttpMethod = HttpMethod.GET

    override def getUri: String = uri

    override def getContent: ChannelBuffer = ???

    override def setContent(content: ChannelBuffer): Unit = ???

    override def isChunked: Boolean = ???

    override def setProtocolVersion(version: HttpVersion): Unit = ???

    override def setChunked(chunked: Boolean): Unit = ???

    override def getProtocolVersion: HttpVersion = HttpVersion.HTTP_1_1

    override def headers(): HttpHeaders = ???
  }
  
  describe("FinagleService") {
    describe("request mock string value") {
      it("should return correct value") {
        val content:CharSequence = "fuga"
        Resource.addMock(Mock("/hoge", content.toString))
        val request = requestBuilder("/hoge")
        val service = new FinagleService()
        service.apply(request).map{response =>
          assert(response.getContent == copiedBuffer(content, Utf8))
        }
      }
    }
    describe("request mock file content") {
      it("should return correct value") {
        val filePath = "./src/test/resources/test.json"
        Resource.addMock(Mock("/hoge", filePath))
        val fileContent:CharSequence = readFile(filePath)
        val request = requestBuilder("/hoge")
        val service = new FinagleService()
        service.apply(request).map{response =>
          assert(response.getContent == copiedBuffer(fileContent, Utf8))
        }
      }
    }
  }
}
