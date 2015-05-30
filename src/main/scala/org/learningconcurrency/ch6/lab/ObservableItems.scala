package org.learningconcurrency.ch6.lab

import rx.lang.scala._
object ObservablesItems extends App {
    
    // items is a factory for an Observable object.
    val o = Observable.items("Pascal", "Java", "Scala")
    
    // Observer, in this case, is anon func for println
    /* subscribe is the callback that responds to the completion,
     * similar to foreach */
    o.subscribe(name => println(s"learned the $name language"))
    o.subscribe(name => println(s"forgot the $name language"))
}