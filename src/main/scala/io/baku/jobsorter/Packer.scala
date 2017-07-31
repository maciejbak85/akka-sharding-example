package io.baku.jobsorter

import scala.util.Random

object Packer {

  def packIt(num: Int, randNumber: Int = Random.nextInt(1000)) : String = {
    Thread.sleep(23)
    s"PCR_${num}_$randNumber"
  }

}
