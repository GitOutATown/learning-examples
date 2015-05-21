package org.learningconcurrency.ch5.lab

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.io.Source
import scala.collection.GenSeq
import org.learningconcurrency.ch5.lab.Timer_1._

object ParHtmlSearch extends App {
    val start = System.nanoTime()
    
    def getHtmlSpec() = Future {
        val url = "http://www.w3.org/MarkUp/html-spec/html-spec.txt"
        val specSrc = Source.fromURL(url)
        try specSrc.getLines.toArray finally specSrc.close()
    }
    
    getHtmlSpec() foreach { case specDoc =>
         
        def search(d: GenSeq[String]): Double = warmedTimed() { 
            d.indexWhere(line => line.matches(".*TEXTAREA.*")) 
        }
         
        val seqtime = search(specDoc)
        println(s"Sequential time $seqtime ms")
         
        val partime = search(specDoc.par)
        println(s"Parallel time $partime ms")
    }
    
    // -------------------------- //
    // Example times without warmup:
    // Sequential time 54.193 ms, Parallel time 24.84 ms
    // Sequential time 56.84 ms,  Parallel time 19.59 ms
    // After warming:
    // Sequential time 1.56 ms
    // Parallel time 1.732 ms
     
    Thread.sleep(4000)
    val end = System.nanoTime()
    println("JVM leavin da haus after " + (((end - start)/1000) / 1000d) + " ms")
}



