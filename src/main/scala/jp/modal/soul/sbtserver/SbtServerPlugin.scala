package jp.modal.soul.sbtserver

import jp.modal.soul.sbtserver.command.server.FinagleServer
import jp.modal.soul.sbtserver.model.entity.Mock
import jp.modal.soul.sbtserver.util._
import sbt.Keys._
import sbt._

import scala.collection.mutable.Map
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try

/**
 * Created by imae on 2015/06/09.
 */
object SbtServerPlugin extends Plugin {
  private[this] final val PORT = """-p|-port"""
  private[this] final val BASE_DIR = """-b|-base"""
  private[this] final val ADD = """-a|-add"""
  private[this] final val LIST = """-l|-list"""

  override lazy val settings = Seq(
    commands ++= Seq(
      sample,
      server,
      mock
    )
  )

  Resource.addMock(Mock("/", "sbt-server is running."))

  lazy val sample = Command.command("hello") { state =>
    println("Hello SBT World!")
    state
  }

  lazy val server = Command.args("server", "<-p|-path PORT_NUMBER> <-b|-base BASE_DIR>") { (state, args) =>
    val withIndex = args.zipWithIndex
    val port = withIndex.find(_._1.trim.matches(PORT)).flatMap{ case (p:String, i:Int) => Try(args(i+1).toInt).toOption }
    val baseDir = withIndex.find(_._1.trim.matches(BASE_DIR)).flatMap{ case (p:String, i:Int) => Try(args(i+1)).toOption }
    baseDir.foreach(Resource.setBaseDir)
    Future(FinagleServer(port))
    state
  }

  lazy val mock = Command.args("mock", "<-a|-add> <URI> <VALUE|RESOURCE_PATH>") { (state, args) =>
    args.head match {
      case a if a.matches(ADD) =>
        val keyValue = Try((args.tail.head, args.tail.tail.mkString(" "))).toOption
        keyValue.foreach{case (k:String, v:String) => Resource.addMock(Mock(k,v))}
        println(s"[SUCCESS]added mock ${args.head} to ${args.tail.mkString(" ")}")
      case a if a.matches(LIST) =>
        Resource.mockList.foreach(m => println(m._2.toString))
      case _ =>
        println("[ERROR]Illegal command format.\nusage:\nmock <-a|-add> <URI> <VALUE|RESOURCE_PATH>")
    }
    state
  }
}
