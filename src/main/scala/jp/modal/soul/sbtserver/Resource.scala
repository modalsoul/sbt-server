package jp.modal.soul.sbtserver

import java.io.{File, BufferedReader, InputStreamReader, FileInputStream}

import jp.modal.soul.sbtserver.model.entity.Mock
import jp.modal.soul.sbtserver.util.Loan

import scala.collection.mutable.Map

/**
 * Created by imae on 2015/06/11.
 */
object Resource {
  private[this] final val CHAR_SET = "UTF-8"
  private[this] var baseDir = "./"
  
  def setBaseDir(arg:String): Unit = baseDir = arg

  def get(uri:String) = {
    println(SbtServerPlugin.registered)
    SbtServerPlugin.registered.get(uri).fold {
      val builder = new StringBuilder
      for {
        in <- Loan(new FileInputStream(uri))
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
