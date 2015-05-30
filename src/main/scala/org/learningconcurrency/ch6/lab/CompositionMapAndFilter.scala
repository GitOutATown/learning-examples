package org.learningconcurrency.ch6.lab

import rx.lang.scala._
import scala.concurrent.duration._
    
object CompositionMapAndFilter extends App {

    val odds = Observable.interval(0.5.seconds)
        .filter(_ % 2 == 1) // Returns an Observable which only emits those items for which a given predicate holds.
        .map(n => s"odd number $n") // Returns an Observable that applies the given function to each item emitted by an Observable and emits the result.
        .take(5) // Returns an Observable that contains only the first n elements.
    
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