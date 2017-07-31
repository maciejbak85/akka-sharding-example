package io.baku.jobsorter

import akka.actor.{Actor, ActorLogging, Props}
import akka.cluster.sharding.ShardRegion.{ExtractEntityId, ExtractShardId}

class PackagingActor extends Actor with ActorLogging {

  override def receive: Receive = {
    case PackIt(num) =>
      val packed = Packer.packIt(num)
      log.info(s"=======> Packed num: $num <=======")
      sender ! Packed(packed)
  }

}

object PackagingActor {
  val props = Props[PackagingActor]

  def shardName = "packager"

  val extractShardId: ExtractShardId = {
    case PackIt(num) =>
      (num % 2).toString
  }

  val extractEntityId: ExtractEntityId = {
    case m: PackIt =>
      (m.num.toString, m)
  }
}