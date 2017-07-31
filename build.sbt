name := "akkaShardingExample"

version := "1.0"

scalaVersion := "2.12.3"

val akkaV = "2.5.3"
val akkaHttpV = "10.0.9"

val akkaDesp = Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaV,
  "com.typesafe.akka" %% "akka-cluster" % akkaV,
  "com.typesafe.akka" %% "akka-remote" % akkaV,
  "com.typesafe.akka" %% "akka-http" % akkaHttpV,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,
  "com.typesafe.akka" %% "akka-stream" % akkaV,
  "com.typesafe.akka" %% "akka-cluster-sharding" % akkaV,
  "com.typesafe.akka" %% "akka-distributed-data" % akkaV,
  "com.typesafe.akka" %% "akka-persistence" % akkaV,
  "com.typesafe.akka" % "akka-cluster-metrics_2.12" % akkaV,
  "com.typesafe.akka" % "akka-slf4j_2.12" % akkaV,
  "com.typesafe.akka" %% "akka-cluster-tools" % akkaV
)

val loggingDeps = Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" % "scala-logging_2.12" % "3.7.1"
)

libraryDependencies ++= akkaDesp ++ loggingDeps ++ Seq(
  "com.twitter" % "chill-akka_2.12" % "0.9.2",
  "org.scalatest" % "scalatest_2.12" % "3.0.3" % "test"
)

maintainer := "Maciej Bąk <maciej.bak.85@gmail.com>"

dockerExposedPorts in Docker := Seq(1600)

dockerEntrypoint in Docker := Seq("sh", "-c", "CLUSTER_IP=`/sbin/ifconfig eth0 | grep 'inet addr:' | cut -d: -f2 | awk '{ print $1 }'` bin/clustering $*")

dockerRepository := Some("akkaclusterbaku")

dockerBaseImage := "java"
enablePlugins(JavaAppPackaging)