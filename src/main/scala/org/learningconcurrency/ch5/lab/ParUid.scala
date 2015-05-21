package org.learningconcurrency.ch5.lab

import org.learningconcurrency.ch5.lab.Timer_1.timed
import java.util.concurrent.atomic._

// Parallel unique IDs - ten million
object ParUid extends App {
    
    private val uid = new AtomicLong(0L)
    val seqtime = timed {
        for (i <- 0 until 10000000) uid.incrementAndGet()
    }
    println(s"Sequential time $seqtime ms")
    val partime = timed {
        for (i <- (0 until 10000000).par) uid.incrementAndGet()
    }
    println(s"Parallel time $partime ms")
}