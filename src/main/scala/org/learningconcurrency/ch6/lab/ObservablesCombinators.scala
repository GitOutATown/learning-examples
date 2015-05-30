package org.learningconcurrency.ch6.lab

import rx.lang.scala._

object ObservablesCombinators extends App {
    

    val roles = Observable.items("The Good", "The Bad", "The Ugly")
    val names = Observable.items("Clint Eastwood", "Lee Van Cleef", "Eli Wallach")
    val zipped = names.zip(roles).map { case (name, role) => s"$name - $role" }

    zipped.subscribe(println(_))

}