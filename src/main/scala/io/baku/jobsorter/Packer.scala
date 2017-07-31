package io.baku.jobsorter

import scala.util.Random

object Packer {

  def packIt(num: Int, randNumber: Int = Random.nextInt(1000)) : String = {
    Thread.sleep(Random.nextInt(5))
    s"PCR_${num}_$randNumber"
  }

}
