package org.learningconcurrency.ch4.lab

import scala.concurrent._
import ExecutionContext.Implicits.global

object FuturesNonFatal extends App {
     val f = Future { throw new InterruptedException }
     val g = Future { throw new IllegalArgumentException }
     // InterruptedException trumps failed and goes straight to the console/top of the stack
     // i.e. Fatal errors are automatically forwarded to the execution context.
     f.failed foreach { case t => println(s"error - $t") }
     g.failed foreach { case t => println(s"error - $t") }
}