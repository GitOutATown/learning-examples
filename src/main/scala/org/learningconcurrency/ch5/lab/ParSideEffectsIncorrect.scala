package org.learningconcurrency.ch5.lab

import scala.collection._

object ParSideEffectsIncorrect extends App {
    
    def intersectionSize(a: GenSet[Int], b: GenSet[Int]): Int = {
        var total = 0
        for (x <- a) if (b contains x) total += 1
        total
    }
    
    val a = (0 until 1000).toSet
    val b = (0 until 1000 by 4).toSet
    val seqres = intersectionSize(a, b)
    val parres = intersectionSize(a.par, b.par)
    println(s"Sequential result - $seqres")
    println(s"Parallel result   - $parres")
    
    /* Non-Synchronization ERROR
    Sequential result - 250
    Parallel result   - 247
    */
}