package org.learningconcurrency.ch6.lab

import rx.lang.scala._
import scala.concurrent.duration._
import scala.io.Source
import Observable._

object CompositionScan4 extends App {

    // Counts number (i.e. ratio) of retries to attempts
    /* Repeat: Returns an Observable that repeats the sequence of items 
     * emitted by the source Observable indefinitely.
     */
    val subscription = shortQuote.retry.repeat.take(100).scan((0,0, "")) {
        // n is accumulator supplied by scan, quote is emitted by observable
        (acc, quote) => // (retries, total count)
            //println("==>acc._3: " + acc._3 + "\nquote: " + quote)
            if (quote == "Retrying...") {
                (acc._1 + 1, acc._2 + 1, quote)
            } 
            else {
                (acc._1, acc._2 + 1, quote)
            } // fed back to accumulator function
    } subscribe(
        emit => process(emit),
        e => println(s"$e being caught, not thrown"), 
        () => println("no more odds")
    )
    
    /* I actually think this is working correctly: counts are in sync with 
     * quote and unsubscribe works. The error catch is not ideal because
     * it's not able to distinguish expected unsubscribe Null Pointer.
     * The null pointer is an unfortunate side effect of unsubscribe and
     * should be refactored out some how...
     */
    def process(emit: (Int, Int, String)) { // (retries, total count, quote)
        println(emit._1 + " / " + emit._2 + " : " + emit._3)
        if(emit._2 >= 10) { // total count > n
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


