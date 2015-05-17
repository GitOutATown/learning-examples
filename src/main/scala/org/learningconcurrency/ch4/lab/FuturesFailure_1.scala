package org.learningconcurrency.ch4.lab

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.io.Source
  
object FuturesFailure_1 extends App {
    
    val urlSpec: Future[String] = Future {
        val invalidUrl = "http://www.w3.org/non-existent-url-spec.txt"
        Source.fromURL(invalidUrl).mkString
    }
    
    urlSpec.failed foreach {
       case t => println(s"exception occurred - $t")
    }
    
    Thread.sleep(1000)
}