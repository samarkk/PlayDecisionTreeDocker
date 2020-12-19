import com.typesafe.sbt.packager.docker._
name := "playdtreeservice"
version := "1.0.0-SNAPSHOT"
lazy val root = (project in file(".")).enablePlugins(PlayScala,JavaAppPackaging,LauncherJarPlugin)
scalaVersion := "2.12.12"
libraryDependencies += guice
libraryDependencies += "ml.combust.mleap" %% "mleap-spark" % "0.16.0"
packageName in Docker := "docker-pdt"

dockerCommands ++= Seq(  Cmd("USER", "root"),  ExecCmd("RUN", "apk", "add", "--no-cache", "bash"))
dockerBaseImage := "openjdk:8-jre-alpine"


