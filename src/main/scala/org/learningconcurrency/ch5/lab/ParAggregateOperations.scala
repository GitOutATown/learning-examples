package org.learningconcurrency.ch5.lab

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.collection.GenSeq
import org.learningconcurrency.ch5.lab.Timer_1._

object ParAggregateOperations extends App {
    ParHtmlSearch.getHtmlSpec() foreach { case specDoc =>
        def allMatches(d: GenSeq[String]) = warmedTimed() {
            val results = d.aggregate("")(
                 (acc, line) =>
                 if (line.matches(".*TEXTAREA.*")) s"$acc\n$line" else acc,
                 (acc1, acc2) => acc1 + acc2
            ) 
        }
        val seqtime = allMatches(specDoc)
        println(s"Sequential time - $seqtime ms")
        val partime = allMatches(specDoc.par)
        println(s"Parallel time   - $partime ms")
    }
    /*
    Sequential time - 3.66 ms
    Parallel time   - 2.871 ms
     
    Sequential time - 3.534 ms
    Parallel time   - 2.438 ms 
    */
    Thread.sleep(5000)
}