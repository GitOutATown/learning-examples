package org.learningconcurrency.ch5.lab

import scala.concurrent._
import ExecutionContext.Implicits.global

object ParNonDeterministicOperation extends App {
    
    ParHtmlSearch.getHtmlSpec() foreach { case specDoc =>
        val patt = ".*TEXTAREA.*"
        val seqresult = specDoc.find(_.matches(patt))
        val parresult = specDoc.par.find(_.matches(patt))
        println(s"Sequential result - $seqresult")
        println(s"Parallel result   - $parresult")
    }
    
    // Searches find different matches
    // Sequential result - Some(    <TEXTAREA>), each block structuring element is regarded as a)
    // Parallel result   - Some(    single line text entry field. (Use the <TEXTAREA> element for)

    
    Thread.sleep(3000)
}