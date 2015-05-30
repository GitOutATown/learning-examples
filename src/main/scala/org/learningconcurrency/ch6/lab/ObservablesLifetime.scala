package org.learningconcurrency.ch6.lab

import rx.lang.scala._

object ObservablesLifetime extends App {
    
    val classics = List("Good, bad, ugly", "Titanic", "Die Hard")
    val movies = Observable.from(classics)
    
    movies.subscribe(new Observer[String] {
        // three separate events
        override def onNext(m: String) = println(s"Movies Watchlist - $m") 
        override def onError(e: Throwable) = println(s"Ooops - $e!")
        override def onCompleted() = println(s"No more movies.")
    })
}