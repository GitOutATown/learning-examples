package org.learningconcurrency.ch6.lab

import rx.lang.scala._

object ObservablesCreate extends App {

  val vms = Observable.apply[String] { obs =>
    obs.onNext("JVM")
    obs.onNext(".NET")
    obs.onNext("DartVM")
    obs.onNext("RageAgainstVM")
    obs.onCompleted()
    Subscription()
  }

  println(s"About to subscribe")
  // Synchronous
  vms.subscribe(println _, e => println(s"oops - $e"), () => println("Done!"))
  println(s"Subscription returned") // How do you know?!
}