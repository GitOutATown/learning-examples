package org.learningconcurrency.ch6.lab

import rx.lang.scala._

object ObservablesExceptions extends App {
    val exc = new RuntimeException
    val o = Observable.items(1, 2) ++ Observable.error(exc)
    o.subscribe(
        x => println(s"number $x"),
        t => println(s"an error occurred: $t")
    )
}
