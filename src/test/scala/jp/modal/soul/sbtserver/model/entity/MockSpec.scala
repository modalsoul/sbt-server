package jp.modal.soul.sbtserver.model.entity

import org.scalatest.{Matchers, FlatSpec}
import jp.modal.soul.sbtserver.util._

/**
 * Created by imae on 2015/06/22.
 */
class MockSpec extends FlatSpec with Matchers {
  "mock" should "return src" in {
    val mock = Mock("/hoge", "fuga")
    mock.resource should be("fuga")
  }
  
  "mock" should "return file contents" in {
    val filePath = "./src/test/resources/test.json"
    val mock = Mock("/hoge", filePath)
    mock.resource should be(readFile(filePath))
  }
}
