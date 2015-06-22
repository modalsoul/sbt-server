name := "sbt-server"

sbtPlugin := true

organization := "jp.modal.soul"

version := "0.1-SNAPSHOT"

scalaVersion := "2.10.4"

val testDependencies = Seq(
  "org.scalatest" % "scalatest_2.10" % "2.2.1" % "test" withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.12.1" % "test" withSources() withJavadoc()
)

libraryDependencies ++= Seq(
  "com.twitter" %% "finagle-http" % "6.25.0"
)++testDependencies

initialCommands := "import jp.modal.soul.sbtserver.SbtServerPlugin._"

