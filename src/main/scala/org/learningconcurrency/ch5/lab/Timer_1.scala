package org.learningconcurrency.ch5.lab

object Timer_1 {
    
    @volatile var dummy: Any = _
    def timed[T](body: => T): Double = {
        val start = System.nanoTime()
        dummy = body
        val end = System.nanoTime()
        ((end - start)/1000) / 1000d
    }
    
    def warmedTimed[T](n: Int = 200)(body: =>T): Double = {
       for (_ <- 0 until n) body
       timed(body)
    }
}






