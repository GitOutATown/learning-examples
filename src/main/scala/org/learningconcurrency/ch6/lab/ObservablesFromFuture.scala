package org.learningconcurrency.ch6.lab

import rx.lang.scala._
import scala.concurrent._
import ExecutionContext.Implicits.global

object ObservablesFromFuture extends App {

    val o = Observable.from(Future {
        Thread.sleep(1500)
        "Back to the Future(s)"
    })

    o.subscribe(println(_))
    
    println("TCB")
    Thread.sleep(2000)
    println("JVM leavin")
}