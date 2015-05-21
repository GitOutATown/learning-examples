package org.learningconcurrency.ch5.lab

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.collection.GenSeq
import org.learningconcurrency.ch5.lab.Timer_1._

object ParNonParallelizableOperations extends App {
    ParHtmlSearch.getHtmlSpec() foreach { case specDoc =>
        def allMatches(d: GenSeq[String]) = warmedTimed() {
            val results = d.foldLeft("") { (acc, line) =>
                if (line.matches(".*TEXTAREA.*")) s"$acc\n$line" else acc
            } 
        }
        val seqtime = allMatches(specDoc)
        println(s"Sequential time - $seqtime ms")
        val partime = allMatches(specDoc.par)
        println(s"Parallel time   - $partime ms")
    }
    
    /*
       Sequential time - 3.522 ms
       Parallel time   - 3.467 ms
     */
    Thread.sleep(5000)
}


