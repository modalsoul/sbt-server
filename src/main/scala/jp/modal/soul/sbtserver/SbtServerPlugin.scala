package jp.modal.soul.sbtserver

import jp.modal.soul.sbtserver.command.server.FinagleServer
import sbt.Keys._
import sbt._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try

/**
 * Created by imae on 2015/06/09.
 */
object SbtServerPlugin extends Plugin {
  private[this] final val PORT = """-p|-port"""
  private[this] final val BASE_DIR = """-b|-base"""

  override lazy val settings = Seq(
    commands ++= Seq(
      sample,
      server
    )
  )

  lazy val sample = Command.command("hello") { state =>
    println("Hello SBT World!")
    state
  }

  lazy val server = Command.args("server", "<args>") { (state, args) =>
    val withIndex = args.zipWithIndex
    val port = withIndex.find(_._1.trim.matches(PORT)).flatMap{ case (p:String, i:Int) => Try(args(i+1).toInt).toOption }
    val baseDir = withIndex.find(_._1.trim.matches(BASE_DIR)).flatMap{ case (p:String, i:Int) => Try(args(i+1)).toOption }
    Future(FinagleServer(port, baseDir))
    state
  }

}
