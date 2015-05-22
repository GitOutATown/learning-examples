package org.learningconcurrency.ch5.lab

import scala.collection._

object ParNonCommutativeOperator extends App {
    
    val doc = mutable.ArrayBuffer.tabulate(20)(i => s"Page $i, ")
    
    def test(doc: GenIterable[String]) {
        val seqtext = doc.seq.reduceLeft(_ + _)
        val partext = doc.par.reduce(_ + _)
        println(s"Sequential result $seqtext\n")
        println(s"Parallel result $partext\n")
    }
    
    test(doc) // both retain order
    test(doc.toSet) // both lose order
}
