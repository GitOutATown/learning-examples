package org.learningconcurrency.ch4.lab

import scala.concurrent._
import ExecutionContext.Implicits.global

object PromisesCreate extends App {
    val p = Promise[String]
    val q = Promise[String]
    
    // Futures waiting
    p.future foreach { // foreach callback
        case x => println(s"p succeeded with '$x'") 
    }
    q.future.failed foreach { case t => println(s"q failed with $t") }
     
    Thread.sleep(1000)
    // completing
    p success "assigned"
    q failure new Exception("not kept")
     
    Thread.sleep(1000)
}