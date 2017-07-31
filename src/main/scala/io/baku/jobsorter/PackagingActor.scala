package io.baku.jobsorter

import akka.actor.{Actor, ActorLogging}
import io.baku.jobsorter.Messages.PackIt

class PackagingActor extends Actor with ActorLogging {

  override def receive: Receive = {
    case PackIt(num) =>
      val packed = Packer.packIt(num)
      sender ! packed

  }

}
