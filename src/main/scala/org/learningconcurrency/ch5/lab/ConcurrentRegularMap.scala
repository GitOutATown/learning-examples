package org.learningconcurrency.ch5.lab

import scala.collection._

// I'm not seeing any behavioral difference between this and ConcurrentTrieMap
object ConcurrentRegularMap extends App {
    val cache = scala.collection.mutable.Map[Int, String]()
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