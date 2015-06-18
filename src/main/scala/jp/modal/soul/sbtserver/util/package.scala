package jp.modal.soul.sbtserver

import java.io.{BufferedReader, InputStreamReader, FileInputStream}

/**
 * Created by imae on 2015/06/18.
 */
package object util {
  def readFile(filePath:String, charSet:String) = {
    val builder = new StringBuilder
    for {
      in <- Loan(new FileInputStream(filePath))
      reader <- Loan(new InputStreamReader(in, charSet))
      buffer <- Loan(new BufferedReader(reader))
    } {
      var line = buffer.readLine()
      while (line != null) {
        builder.append(line)
        line = buffer.readLine()
      }
    }
    builder.toString()
  }
}
