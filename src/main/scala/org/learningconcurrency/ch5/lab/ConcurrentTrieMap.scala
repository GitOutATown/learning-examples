package org.learningconcurrency.ch5.lab

import scala.collection._

// I'm not seeing any behavioral difference between this and ConcurrentRegularMap
// I don't think this example is constructed properly because I don't see that these loops run in parallel. The print statements (and just reading the code) indicates that they are running sequentially.
object ConcurrentTrieMap extends App {
    val cache = new concurrent.TrieMap[Int, String]()
    for (i <- 0 until 100) {
        println("Pos loop i: " + i)
        cache(i) = i.toString
    }
    for ((number, string) <- cache.par) {
        println("Neg loop i: " + number)
        cache(-number) = s"-$string"
    }
    println(s"cache - ${cache.keys.toList.sorted}")
}