package jp.modal.soul.sbtserver

import jp.modal.soul.sbtserver.command.FinagleServer
import sbt.Keys._
import sbt._

import scala.util.Try

/**
 * Created by imae on 2015/06/09.
 */
object SbtServerPlugin extends Plugin {
  private[this] final val PORT = "-p"

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
    val port = args.zipWithIndex.find(_._1.trim == PORT).flatMap{ case (p:String, i:Int) => Try(args(i+1).toInt).toOption }
    FinagleServer(port)
    state
  }
}
