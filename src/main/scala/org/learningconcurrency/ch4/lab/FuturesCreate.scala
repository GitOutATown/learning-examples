package org.learningconcurrency.ch4.lab

import scala.concurrent._
import ExecutionContext.Implicits.global

object FuturesCreate extends App {
    
    // This is a worthless example
    
    Future {
        println("the future is here") 
    }
     
    println("the future is coming")
    Thread.sleep(1000)
}