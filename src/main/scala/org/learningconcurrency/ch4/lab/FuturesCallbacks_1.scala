package org.learningconcurrency.ch4.lab

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.io.Source
import scala.util.{Try, Success, Failure}

object FuturesCallbacks_1 extends App {
    
    def getUrlSpec(): Future[List[String]] = Future {
        val url = "http://www.w3.org/Addressing/URL/url-spec.txt"
        val buf = Source.fromURL(url)
        val lines = try buf.getLines.toList finally buf.close()
        lines
    }
    
    val urlSpec: Future[List[String]] = getUrlSpec()
    
    def find(lines: List[String], keyword: String): String =
        lines.zipWithIndex collect {
            case (line, n) if line.contains(keyword) => (n, line)
        } mkString("\n")
       
    urlSpec.onComplete {
        /* This pattern I'm using here is totally legit, but note that
         * `onSuccess` is just an alias for `foreach` to harmonize with 
         * `onFailure`. (Both onSuccess and onFailure is scheduled to be 
         * deprecated for 2.12 because they are redundant due to `foreach` 
         * and `failed.foreach`)
         * --Victor Klang
         * https://groups.google.com/forum/#!topic/scala-user/RzAiKRZ07Ig
         */
        case Success(lines) => { // extends Try[T]
            val telnetLines = find(lines, "telnet")
            println("telnet lines: " + "\n" + telnetLines)
            
            val completeTime = System.currentTimeMillis
            println("onComplete, lines.length: " + lines.length)
            println("onComplete took " + 
                ((completeTime - startTime) / 1000d) + " seconds.")
        }
        case Failure(e) => println(e) // extends Try[T]
    }
    
    val startTime = System.currentTimeMillis
    println("TCB")
    Thread.sleep(1000)
    println("JVM leavin' da haus!")
}




