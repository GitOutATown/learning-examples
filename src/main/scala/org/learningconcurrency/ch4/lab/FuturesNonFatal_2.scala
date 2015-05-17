package org.learningconcurrency.ch4.lab

import scala.concurrent._
import ExecutionContext.Implicits.global

object FuturesNonFatal_2 extends App {
    val g = Future { throw new IllegalArgumentException }
    val f = Future { throw new InterruptedException }
    // InterruptedException trumps failed and goes straight to the console/top of the stack
    // i.e. Fatal errors are automatically forwarded to the execution context.
    g.failed foreach { case t => println(s"error - $t") } 
    f.failed foreach { case t => println(s"error - $t") }
    
}