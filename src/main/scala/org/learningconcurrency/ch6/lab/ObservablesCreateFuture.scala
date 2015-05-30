package org.learningconcurrency.ch6.lab

object ObservablesCreateFuture extends App {
    import rx.lang.scala._
    import scala.concurrent._
    import ExecutionContext.Implicits.global

    val f = Future {
        Thread.sleep(1500)
        "Back to the Future(s)"
    }

    val o = Observable.apply[String] { obs =>
        f foreach {
            case s =>
                /*
                 * How does it tell which is which? How are these paired? 
                 * Well, in this example, theres' only one emission. But is 
                 * that always the case? What about a collection of futures? 
                 * Does that make sense?
                 */
                obs.onNext(s)
                obs.onCompleted()         
        }
        f.failed foreach {
            case t => obs.onError(t)
        }
        Subscription()
    }

    o.subscribe(println(_)) // Asynchronous (because of Future, I guess. Is that the only way?)
    println("TCB")
    Thread.sleep(2000)
    println("JVM leavin")
}
