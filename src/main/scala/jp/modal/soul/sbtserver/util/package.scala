package jp.modal.soul.sbtserver

import java.io.{BufferedReader, FileInputStream, InputStreamReader}

/**
 * Created by imae on 2015/06/18.
 */
package object util {
  def readFile(filePath:String)(implicit charSet:String = "UTF-8") = {
    val builder = new StringBuilder
    for {
      in <- Loan(new FileInputStream(filePath))
      reader <- Loan(new InputStreamReader(in, charSet))
      buffer <- Loan(new BufferedReader(reader))
    } {
      var line = buffer.readLine()
      while (line != null) {
        builder.append(s"$line\n")
        line = buffer.readLine()
      }
    }
    builder.toString()
  }

  class Loan[T <: { def close() }] private (value: T) {
    def foreach[U](f: T => U): U = try {
      f(value)
    } finally {
      value.close()
    }
  }

  object Loan {
    def apply[T <: { def close() }](value: T) = new Loan(value)
  }
}
