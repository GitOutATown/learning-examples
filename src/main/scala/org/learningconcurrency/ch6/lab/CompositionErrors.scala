package org.learningconcurrency.ch6.lab

import rx.lang.scala._

object CompositionErrors extends App {
    // TODO: I DO NOT HAVE A CLEAR UNDERSTANDING OF WHAT'S GOING ON HERE...

    val status = Observable[String] { sub =>
        sub.onNext("ok")
        sub.onNext("still ok")
        sub.onError(new Exception("very bad"))
    }

    val fixedStatus = status.onErrorReturn(e => e.getMessage)
    fixedStatus.subscribe(println(_)) // Maybe onErrorResume automatically gets the next call? Looks like that might be what's going on...

    val continuedStatus = status.onErrorResumeNext(e => 
        Observable.items("better", "much better"))
        continuedStatus.subscribe(println(_))
}