name := "underscore_cats"

version := "0.1"

scalaVersion := "2.13.0"

lazy val akkaVersion = "2.6.5"
lazy val logBack = "1.2.3"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % logBack
)
