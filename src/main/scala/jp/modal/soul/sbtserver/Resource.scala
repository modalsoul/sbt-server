package jp.modal.soul.sbtserver

import java.io.{BufferedReader, FileInputStream, InputStreamReader}
import jp.modal.soul.sbtserver.model.entity.Mock

import scala.collection.mutable.{Map => MutableMap}

import jp.modal.soul.sbtserver.util.Loan

/**
 * Created by imae on 2015/06/11.
 */
object Resource {
  private[this] final val DEFAULT_BASE_DIR = "."
  private[this] final val CHAR_SET = "UTF-8"
  private[this] var baseDir = DEFAULT_BASE_DIR
  private[this] val registered = MutableMap.empty[String, String]
  private[this] final val path = (target:String) => {
    if(target.startsWith("./")) target
    else s"./$target"
  }

  def setBaseDir(arg:String): Unit = baseDir = arg
  def addMock(mock:Mock):Unit = registered(mock.path) = mock.resource

  def get(target:String) = {
    registered.get(target).fold {
      val builder = new StringBuilder
      for {
        in <- Loan(new FileInputStream(path(target)))
        reader <- Loan(new InputStreamReader(in, CHAR_SET))
        buffer <- Loan(new BufferedReader(reader))
      } {
        var line = buffer.readLine()
        while (line != null) {
          builder.append(line)
          line = buffer.readLine()
        }
      }
      builder.toString()
    }{
      identity
    }
  }
}
