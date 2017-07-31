package io.baku.jobsorter

import akka.actor.{ActorRef, ActorSystem}
import akka.cluster.sharding.{ClusterSharding, ClusterShardingSettings}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._

object WebServer {
  def main(args: Array[String]) {

    implicit val system = ActorSystem("zyztem")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher
    val config = ConfigFactory.load()

    //val packagingActor = system.actorOf(FromConfig.props(PackagingActor.props), "packBoss")

    val counterRegion: ActorRef = ClusterSharding(system).start(
      typeName = PackagingActor.shardName,
      entityProps = PackagingActor.props,
      settings = ClusterShardingSettings(system),
      extractEntityId = PackagingActor.extractEntityId,
      extractShardId = PackagingActor.extractShardId)

    val route =
      path("pack" / IntNumber) { (workId) =>
        get {
          val resp = counterRegion.ask(PackIt(workId))(5 seconds).mapTo[Packed]
          complete(resp)
        }
      }

    val httpPort = config.getInt("http.port")
    Http().bindAndHandle(route, "localhost", httpPort)

    println(s"Server online at http://localhost:$httpPort/\n")

  }
}