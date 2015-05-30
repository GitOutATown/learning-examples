package org.learningconcurrency.ch4.lab

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.io.Source
import scala.util.{Success, Failure}
    
object FuturesMap extends App {
    
    // Futures
    val buildFile = Future { Source.fromFile("build.sbt").getLines }
    val gitignoreFile = Future { Source.fromFile(".gitignore-FAIL").getLines }

    // Future-to-Future chaining with map (callback creates new Future)
    /* Creates a new future by applying a function to the successful result 
     * of this future. If this future is completed with an exception then the 
     * new future will also contain this exception.
     */
    val longestBuildLine = buildFile.map(lines => lines.maxBy(_.length))
    
    // Future-to-Future chaining with map
    val longestGitignoreLine = for (
         lines <- gitignoreFile // map
    ) yield lines.maxBy(_.length)

    longestBuildLine onComplete {
        case Success(line) => println(s"The longest build line is '$line'")
        case Failure(e) => println("longestBuildLine failed\n" + e)
    }
    
    /* foreach asynchronously processes the value in the future once the 
     * value becomes available. Will not be called if the future fails.
     */
    longestGitignoreLine foreach {
        case line => println(s"The longest .gitignore line is '$line'")
    }

    longestGitignoreLine.failed foreach {
        case line => println(s"No longest gitignore line, because ${line.getMessage}")
    }
    
    println("TCB")
    Thread.sleep(1000)
}

