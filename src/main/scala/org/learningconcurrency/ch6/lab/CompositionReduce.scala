package org.learningconcurrency.ch6.lab

import rx.lang.scala._
import scala.concurrent.duration._
import scala.io.Source

object CompositionReduce extends App {
    
    val shortQuotesCollection = 
        (shortQuote ++ shortQuote ++ shortQuote).foldLeft("") { (acc, quote) =>
            s"$acc $quote\n\n"
    }

    shortQuotesCollection.subscribe(println(_))
    
    def shortQuote = randomQuote.retry.take(5).filter(_ != "Retrying...")
    
    def randomQuote = Observable.apply[String] { obs =>
        val url = "http://www.iheartquotes.com/api/v1/random?" +
            "show_permalink=false&show_source=false"
        obs.onNext(Source.fromURL(url).getLines.mkString)
        obs.onCompleted()
        Subscription()
    }

}