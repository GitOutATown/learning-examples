package org.learningconcurrency.ch5.lab

import scala.collection._

object ParNonAssociativeOperator extends App {
    
    def test(doc: GenIterable[Int]) {
        val seqtext = doc.seq.reduceLeft(_ - _)
        val partext = doc.par.reduce(_ - _)
        println(s"Sequential result: $seqtext\n")
        println(s"Parallel result:   $partext\n")
    }
    
    // Parallel result is random and meaningless
    // Sequential result: -435
    // Parallel result:    125
    test(0 until 30)
}