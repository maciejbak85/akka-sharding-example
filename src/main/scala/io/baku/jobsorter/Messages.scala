package io.baku.jobsorter

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

object Messages {

  case class Packed(id: String)

  object Packed extends SprayJsonSupport with DefaultJsonProtocol {
    implicit val packedJson = jsonFormat1(Packed.apply)
  }

}
