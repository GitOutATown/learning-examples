package org.learningconcurrency.ch5.lab

import scala.concurrent._
import ExecutionContext.Implicits.global

object ParFindIndexOperation extends App {
    
    ParHtmlSearch.getHtmlSpec() foreach { case specDoc =>
        val patt = ".*TEXTAREA.*"
        val seqresult = specDoc.find(_.matches(patt))
        val index = specDoc.par.indexWhere(_.matches(patt))
        val parresult = if (index != -1) Some(specDoc(index)) else None
        println(s"Sequential result - $seqresult")
        println(s"Parallel result   - $parresult")
    }
    // Both searches now match with par using index
    // Sequential result - Some(    <TEXTAREA>), each block structuring element is regarded as a)
    // Parallel result   - Some(    <TEXTAREA>), each block structuring element is regarded as a)

    
    Thread.sleep(4000)
    println("JVM leavin")
}