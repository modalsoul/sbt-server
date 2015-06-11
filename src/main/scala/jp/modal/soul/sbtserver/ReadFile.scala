package jp.modal.soul.sbtserver

import java.io.{BufferedReader, InputStreamReader, FileInputStream}

import jp.modal.soul.sbtserver.util.Loan

/**
 * Created by imae on 2015/06/11.
 */
object ReadFile {
  private[this] final val CHAR_SET = "UTF-8"
  
  def src(uri:String) = {
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
    builder.toString()
  }
}
