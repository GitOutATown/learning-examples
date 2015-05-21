package org.learningconcurrency.ch5.lab

import scala.collection._
import scala.util.Random
import org.learningconcurrency.ch5.lab.Timer_1.timed
    
object Timer_lab_1 extends App {
    
    val numbers = Random.shuffle(Vector.tabulate(5000000)(i => i))
    val seqtime = timed { numbers.max }
    println(s"Sequential time $seqtime ms")
    val partime = timed { numbers.par.max } // Parallel
    println(s"Parallel time $partime ms")

    // Examples:
    // Sequential time 169.079 ms
    // Parallel time 124.499 ms
}