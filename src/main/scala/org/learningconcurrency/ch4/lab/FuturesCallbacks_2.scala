package org.learningconcurrency.ch4.lab

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.io.Source

object FuturesCallbacks_2 extends App {

    def getUrlSpec(): Future[List[String]] = Future {
        /* 
         * Refered to as the "body", this block is passed to
         * Future.apply as the asynchronous computation that will,
         * when completed, result in a value according to its type 
         * or a non-fatal exception.
         */
        val url = "http://www.w3.org/Addressing/URL/url-spec.txt"
        val buf = Source.fromURL(url)
        val lines = try buf.getLines.toList finally buf.close
        lines
    }
    
    val urlSpec: Future[List[String]] = getUrlSpec()
    
    def find(lines: List[String], keyword: String): String = {
        lines.zipWithIndex collect {
            case (line, n) if (line.contains(keyword)) => (n, line)
        } mkString("\n")
    }
        
    urlSpec foreach { // Will not be called if the future fails.
        case lines => {
            val completedTime = System.currentTimeMillis
            println("\nget telnet spec took " + 
                ((completedTime - startTime)/1000d) + " seconds.")
            println("\ntelNet spec:\n" + find(lines, "telnet"))
            println("\nforeach, lines.length: " + lines.length)
        }
    }
    
    urlSpec foreach { // Will not be called if the future fails.
        case lines => {
            val completedTime = System.currentTimeMillis
            println("\nget password spec took " + 
                ((completedTime - startTime)/1000d) + " seconds.")
            println("\npassword spec:\n" + find(lines, "password"))
            println("\nforeach, lines.length: " + lines.length)
        }
    }
    
    val startTime = System.currentTimeMillis
    println("callback registered, gazing out window...")
    Thread.sleep(10000)
}




