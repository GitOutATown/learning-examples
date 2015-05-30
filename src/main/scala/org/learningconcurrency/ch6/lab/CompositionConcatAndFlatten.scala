package org.learningconcurrency.ch6.lab

import rx.lang.scala._
import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
import scala.io.Source
    
object CompositionConcatAndFlatten extends App {

    def fetchQuote(): Future[String] = Future {
        blocking {
            val url = "http://www.iheartquotes.com/api/v1/random?show_permalink=false&show_source=false"
            Source.fromURL(url).getLines.mkString
        }
    }

    def fetchQuoteObservable(): Observable[String] = Observable.from(fetchQuote())

    def quotes: Observable[Observable[String]] = {
        /* >>>
         * Note that the inner map call transforms an Observable[String] instance,
         * which contains the quote text, to another Observable[String] instance, 
         * which contains the quote prefix number. The outer map call transforms
         * the Observable[Long] object, which contains the first four numbers, to
         * an Observable[Observable[String]] instance, which contains Observable 
         * objects emitting separate quotes.
         * <<< 
         */
        Observable.interval(0.5.seconds).take(5).map { // outer map
            n => fetchQuoteObservable().map(txt => s"$n) $txt") // inner map
        }
    }
    
    // concat maintains event order of Observables sequence
    println(s"Using concat")
    quotes.concat.subscribe(println(_))

    Thread.sleep(6000)

    /* The remaining three examples, flatten, flatMap/map, and for-comprehension,
     * order events by their arrival in time (not by sequence order of Observables) */
    
    println(s"Now using flatten")
    /* >>> Invoking the flatMap method is semantically equivalent
     *     to calling map followed by flatten. <<< */
    quotes.flatten.subscribe(println(_))

    Thread.sleep(6000)

    println(s"Now using flatMap")
    Observable.interval(0.5.seconds).take(5).flatMap({
        n => fetchQuoteObservable().map(txt => s"$n) $txt")
    }).subscribe(println(_))

    Thread.sleep(6000)

    println(s"Now using good ol' for-comprehensions")
    val qts = for {
        n   <- Observable.interval(0.5.seconds).take(5) // flatMap
        txt <- fetchQuoteObservable() // map
    } yield s"$n) $txt"
    qts.subscribe(println(_))

    Thread.sleep(6000)

}