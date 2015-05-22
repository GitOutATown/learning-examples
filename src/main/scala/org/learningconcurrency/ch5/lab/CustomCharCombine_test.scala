package org.learningconcurrency.ch5.lab

import org.learningconcurrency.ch5.lab.Timer_1._
import org.learningconcurrency.ch5.lab.CustomParString._

object CustomCharCombiner_test extends App {
   val txt = "A custom txt" * 25000
   val partxt = new ParString(txt)
   val seqtime = warmedTimed(250) { txt.filter(_ != ' ') }
   val partime = warmedTimed(250) { partxt.filter(_ != ' ') }
   
   println(s"seqtime: $seqtime")
   println(s"partime: $partime")
}