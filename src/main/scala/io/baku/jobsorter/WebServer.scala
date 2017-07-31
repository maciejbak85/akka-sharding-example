package io.baku.jobsorter

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.pattern.ask
import akka.routing.FromConfig
import io.baku.jobsorter.Messages.{PackIt, Packed}

import scala.io.StdIn
import scala.concurrent.duration._

object WebServer {
  def main(args: Array[String]) {

    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val packagingActor = system.actorOf(FromConfig.props(PackagingActor.props), "packBoss")

    val route =
      path("pack" / IntNumber) { (workId) =>
        get {
          val resp = packagingActor.ask(PackIt(workId))(5 seconds).mapTo[Packed]
          complete(resp)
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}