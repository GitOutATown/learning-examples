package org.learningconcurrency.ch4.lab

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.io.Source
import scala.util.{Success, Failure}
  
object FuturesMap extends App {
  
  val buildFile = Future { Source.fromFile("build.sbt").getLines }
  val gitignoreFile = Future { Source.fromFile(".gitignore-FAIL").getLines }

  // Future-to-Future chaining with map
  val longestBuildLine = buildFile.map(lines => lines.maxBy(_.length))
  
  // Future-to-Future chaining with map
  val longestGitignoreLine = for (
      lines <- gitignoreFile // map
  ) yield lines.maxBy(_.length)

  longestBuildLine onComplete {
      case Success(line) => println(s"the longest build line is '$line'")
      case Failure(e) => println("longestBuildLine failed\n" + e)
  }
  
  longestGitignoreLine foreach {
      case line => println(s"the longest .gitignore line is '$line'")
  }

  longestGitignoreLine.failed foreach {
      case line => println(s"no longest line, because ${line.getMessage}")
  }
  
  println("TCB")
  Thread.sleep(1000)
}

