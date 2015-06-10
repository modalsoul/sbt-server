name := "sbt-server"

sbtPlugin := true

organization := "jp.modal.soul"

version := "0.1-SNAPSHOT"

scalaVersion := "2.10.4"

val testDependencies = Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test" withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.12.1" % "test" withSources() withJavadoc()
)

libraryDependencies ++= testDependencies

initialCommands := "import jp.modal.soul.sbtserver.SbtServerPlugin._"

