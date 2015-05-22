package org.learningconcurrency.ch5.lab

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.collection._
import org.learningconcurrency.ch4.lab.FuturesCallbacks_2
import ParHtmlSearch.getHtmlSpec
import FuturesCallbacks_2.getUrlSpec

/**** mutable.HashSet NOT thread-safe ****/
/** Different result sizes each run ****/
object ConcurrentWrong extends App {
     
    def intersection(a: GenSet[String], b: GenSet[String]) = {
        val result = new mutable.HashSet[String] // not thread-safe
        for (x <- a.par) if (b contains x) result.add(x)
        result
    }
     
    val ifut = for {
        htmlSpec <- getHtmlSpec()
        urlSpec <- getUrlSpec()
    } yield {
        val htmlWords = htmlSpec.mkString.split("\\s+").toSet
        val urlWords = urlSpec.mkString.split("\\s+").toSet
        intersection(htmlWords, urlWords)
    }
     
    ifut onComplete { 
        case t => {
            println(s"Result: $t")
            println("Result size: " + t.get.size)
        }
    }
    // Different result sizes:
    // Result size: 557
    // Result size: 665
    // Result size: 657
    Thread.sleep(4000)
}


