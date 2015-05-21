package org.learningconcurrency.ch5.lab

import org.learningconcurrency.ch5.lab.Timer_1.timed
import scala.concurrent.forkjoin.ForkJoinPool
import scala.util.Random
import scala.collection._
  
object ParConfig extends App {
    
    val fjpool = new ForkJoinPool(2)
    val customTaskSupport = new parallel.ForkJoinTaskSupport(fjpool)
    val numbers = Random.shuffle(Vector.tabulate(5000000)(i => i))
    val partime = timed {
        val parnumbers = numbers.par
        parnumbers.tasksupport = customTaskSupport
        val n = parnumbers.max
        println(s"largest number $n")
    }
    println(s"Parallel time $partime ms")
    // pool 2: 141.819, 108.132, 120.755, 111.218, 125.191   
    // pool 4: 136.61, 125.96   
}