package org.learningconcurrency.ch6.lab

import rx.lang.scala._
import scala.concurrent.duration._
import scala.io.Source
import Observable._

object CompositionScan3 extends App {

    // Counts number (i.e. ratio) of retries to attempts
    /* Repeat: Returns an Observable that repeats the sequence of items 
     * emitted by the source Observable indefinitely.
     */
    val subscription = shortQuote.retry.repeat.take(100).scan((0,0)) {
        // n is accumulator supplied by scan, quote is emitted by observable
        (acc, quote) => // (retries, total count)
            if (quote == "Retrying...") {
                (acc._1 + 1, acc._2 + 1)
            } 
            else {
                (acc._1, acc._2 + 1)
            } // fed back to accumulator function
    } subscribe(
        /*
         * This pattern is very limited in that it seems to only be able to
         * subscribe to what the scan is returning, which is the accumulator
         * but I'm also now wanting the quote which subscribe doesn't have
         * access to since it can only return the accumulator.
         */
        acc => process(acc),
        e => println(s"unexpected $e \nbeing caught, but not thrown here..."), 
        () => println("no more odds")
    )
    
    def process(acc: (Int, Int)) { // (retries, total count)
        println(acc._1 + " / " + acc._2)
        if(acc._2 >= 10) {
            println("~~unsubscribing")
            subscription.unsubscribe()
        }
    }

    def randomQuote = Observable.apply[String] { obs =>
        val url = "http://www.iheartquotes.com/api/v1/random?" +
            "show_permalink=false&show_source=false"
        obs.onNext(Source.fromURL(url).getLines.mkString)
        obs.onCompleted()
        Subscription()
    }

    // retry catches this error
    def errorMessage = items("Retrying...") ++ error(new Exception)
  
    // callback
    def shortQuote = for {
        txt     <- randomQuote
        message <- if (txt.length < 100) items(txt) else errorMessage
    } yield message
}


