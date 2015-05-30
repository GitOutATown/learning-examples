package org.learningconcurrency.ch6.lab

import scala.concurrent.duration._
import rx.lang.scala._

object ObservablesTimer extends App {
    
    /* Asynchronous, invoked on internal Rx thread pool,
     * in unspecified order.
     */
    val o = Observable.timer(2.second)
    o.subscribe(_ => println("Timeout!"))
    o.subscribe(_ => println("Another timeout!"))
    println("TCB")
    Thread.sleep(4000)
    println("After sleep")
}