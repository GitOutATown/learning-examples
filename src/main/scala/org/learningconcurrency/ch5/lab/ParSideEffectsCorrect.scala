package org.learningconcurrency.ch5.lab

import scala.collection._

object ParSideEffectsCorrect extends App {
    
    def intersectionSize(a: GenSet[Int], b: GenSet[Int]): Int = {
        var total = a.count(x => b contains x)
        total
    }
    
    val a = (0 until 1000).toSet
    val b = (0 until 1000 by 4).toSet
    val seqres = intersectionSize(a, b)
    val parres = intersectionSize(a.par, b.par)
    println(s"Sequential result - $seqres")
    println(s"Parallel result   - $parres")
    
    /* Consistently accurate with:
    Sequential result - 250
    Parallel result   - 250
    */
}