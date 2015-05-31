package org.learningconcurrency.ch6.lab

import rx.lang.scala._
import scala.concurrent.duration._
import scala.io.Source
import Observable._

object CompositionScan2 extends App {

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
    } subscribe(acc => println(acc._1 + " / " + acc._2))

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


