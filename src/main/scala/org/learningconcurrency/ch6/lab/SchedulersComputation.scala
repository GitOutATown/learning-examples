package org.learningconcurrency.ch6.lab

import rx.lang.scala._

object SchedulersComputation extends App {

    val scheduler = schedulers.ComputationScheduler()
    val numbers = Observable.from(0 until 20)
    // TODO: NEED TO OBTAIN THREAD NAME FOR println
    numbers.subscribe(n => println(s"num $n"))
    numbers.observeOn(scheduler).subscribe(n => println(s"num $n"))

    Thread.sleep(2000)
    println("END")
}


