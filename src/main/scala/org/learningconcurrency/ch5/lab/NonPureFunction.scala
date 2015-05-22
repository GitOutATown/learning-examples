package org.learningconcurrency.ch5.lab

import scala.collection._
import java.util.concurrent.atomic._

object NonPureFunction extends App {

    val uid = new AtomicInteger(0)
    val f = (x: Int) => (x, uid.incrementAndGet())
    val uids: GenSeq[(Int, Int)] = (0 until 10000).par.map(f)
    println(uids)
    
    // Non-deterministic results
    // ParVector((0,1), (1,5), (2,7), (3,8), (4,9), (5,10), (6,11), (7,12), (8,14), (9,16)...
    // ParVector((0,2), (1,6), (2,9), (3,13), (4,16), (5,19), (6,22), (7,25), (8,29), (9,31)...
    
    Thread.sleep(5000)
    println("JVM leavin")
}