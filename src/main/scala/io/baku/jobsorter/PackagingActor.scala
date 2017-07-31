package io.baku.jobsorter

import akka.actor.{Actor, ActorLogging, Props}
import io.baku.jobsorter.Messages.{PackIt, Packed}

class PackagingActor extends Actor with ActorLogging {

  override def receive: Receive = {
    case PackIt(num) =>
      val packed = Packer.packIt(num)
      log.info(s"Packed num: $num")
      sender ! Packed(packed)
  }

}

object PackagingActor {
  val props = Props[PackagingActor]
}