package org.learningconcurrency.ch5.lab

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.collection._
import org.learningconcurrency.ch4.lab.FuturesCallbacks_2
import ParHtmlSearch.getHtmlSpec
import FuturesCallbacks_2.getUrlSpec
/************************************************/
import java.util.concurrent.ConcurrentSkipListSet
import scala.collection.convert.decorateAsScala._

object ConcurrentSkipList extends App {
     
    def intersection(a: GenSet[String], b: GenSet[String]) = {
        val skiplist = new ConcurrentSkipListSet[String]
        for (x <- a.par) if (b contains x) skiplist.add(x)
        val result: Set[String] = skiplist.asScala
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
    // Consistent result size
    // Result size: 665
    Thread.sleep(5000)
}


