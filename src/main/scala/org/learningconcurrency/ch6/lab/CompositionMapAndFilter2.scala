package org.learningconcurrency.ch6.lab

import rx.lang.scala._
import scala.concurrent.duration._
    
object CompositionMapAndFilter2 extends App {

    // for-comp more concise
    val odds = for{
        n <- Observable.interval(0.5.seconds)
        if (n % 2 == 1 && n <= 9)
    } yield s"odd number $n"
    
    // Returns Subscription with defs unsubscribe and isSubscribed.
    // Defines callbacks for onNext, onError, onCompleted
    odds.subscribe(
        println(_),
        e => println(s"unexpected $e"), 
        () => println("no more odds")
    ) 

    val evens = for {
        n <- Observable.from(0 until 9)
        if n % 2 == 0
    } yield s"even number $n"
    
    evens.subscribe(println(_))
    
    println("BOTTOM, before sleep...")
    Thread.sleep(6000)
    println("END")
}