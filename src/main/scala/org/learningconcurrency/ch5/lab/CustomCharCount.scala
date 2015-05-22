package org.learningconcurrency.ch5.lab

import org.learningconcurrency.ch5.lab.Timer_1._
import org.learningconcurrency.ch5.lab.CustomParString._

import scala.collection.parallel._

object CustomCharCount extends App {
    
    val txt = "A custom text " * 250000
    val partxt = new ParString(txt)
  
    val seqtime = warmedTimed(50) {
        txt.foldLeft(0)((count, ch) => 
            if (Character.isUpperCase(ch)) count + 1 else count)
    }
  
    println(s"Sequential time - $seqtime ms")

    val partime = warmedTimed(50) {
        partxt.aggregate(0)((count, ch) => if (Character.isUpperCase(ch)) count + 1 else count, _ + _)
    }

    println(s"Parallel time   - $partime ms")

}