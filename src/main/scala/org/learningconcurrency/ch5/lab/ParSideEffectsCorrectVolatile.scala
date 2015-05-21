package org.learningconcurrency.ch5.lab

import scala.collection._
import java.util.concurrent.atomic._

object ParSideEffectsCorrectVolatile extends App {
    
    def intersectionSize(a: GenSet[Int], b: GenSet[Int]): Int = {
        var count = new AtomicInteger(0)
        for (x <- a) if (b contains x) count.incrementAndGet()
        count.get
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