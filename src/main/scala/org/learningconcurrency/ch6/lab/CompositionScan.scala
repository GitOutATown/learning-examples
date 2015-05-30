package org.learningconcurrency.ch6.lab

import rx.lang.scala._
import scala.concurrent.duration._
import scala.io.Source
import Observable._

object CompositionScan extends App {

    // Counts number (i.e. ratio) of retries to attempts
    /* Repeat: Returns an Observable that repeats the sequence of items 
     * emitted by the source Observable indefinitely.
     */
    shortQuote.retry.repeat.take(100).scan(0) {
        (n, quote) => if (quote == "Retrying...") n + 1 else n // accumulator function
    } subscribe(n => println(s"$n / 100"))

    def randomQuote = Observable.apply[String] { obs =>
        val url = "http://www.iheartquotes.com/api/v1/random?" +
            "show_permalink=false&show_source=false"
        obs.onNext(Source.fromURL(url).getLines.mkString)
        obs.onCompleted()
        Subscription()
    }

    def errorMessage = items("Retrying...") ++ error(new Exception)
  
    def shortQuote = for {
        txt     <- randomQuote
        message <- if (txt.length < 100) items(txt) else errorMessage
    } yield message
}