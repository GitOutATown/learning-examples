package org.learningconcurrency.ch5.lab

import org.learningconcurrency.ch5.lab.Timer_1._

object ParNonParallelizableCollections extends App {
    
    val list = List.fill(1000000)("")
    val vector = Vector.fill(1000000)("")
    println(s"list conversion time: ${warmedTimed()(list.par)} ms")
    println(s"vector conversion time: ${warmedTimed()(vector.par)} ms")

    // list conversion time: 12.48 ms
    // vector conversion time: 0.002 ms
}