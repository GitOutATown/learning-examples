package org.learningconcurrency.ch5.lab

import org.learningconcurrency.ch5.lab.Timer_1._
import scala.collection.parallel._

object CustomCharCount_DIAG extends App {
    
    val txt = "A custom text " * 250000
    val partxt = new ParString_DIAG(txt)
  
    val seqtime = timed {
        var foldLeftCnt = 0
        txt.foldLeft(0)((count, ch) => 
            if (Character.isUpperCase(ch)) {
                foldLeftCnt +=1
                count + 1
            }
            else {
                foldLeftCnt +=1
                count
            }
        )
        println(s"=========>foldLeftCnt: $foldLeftCnt")
    }
  
    println(s"Sequential time - $seqtime ms")

    val partime = timed {
        var parAggCnt = 0
        partxt.aggregate(0)((count, ch) => 
            if (Character.isUpperCase(ch)) {
                parAggCnt += 1
                count + 1
            } 
            else {
                parAggCnt += 1
                count
            }
            , _ + _
        )
        println(s"=========>parAggCnt: $parAggCnt")
    }

    println(s"Parallel time   - $partime ms")

}

class ParString_DIAG(val str: String) extends immutable.ParSeq[Char] {
    
    println("========>ParString")
    
    def apply(i: Int) = str.charAt(i)
  
    def length = str.length
  
    def seq = new collection.immutable.WrappedString(str)
  
    def splitter = new ParStringSplitter_DIAG(str, 0, str.length)

    //override def newCombiner = new ParStringCombiner
}

class ParStringSplitter_DIAG(
        private val s: String, 
        private var i: Int, 
        private val limit: Int
    ) extends SeqSplitter[Char] {
    
    val sLength = s.length()
    println(s"ParStringSplitter TOP, s.length:$sLength i:$i limit:$limit")
    
    final def hasNext = i < limit
    
    final def next = {
        val r = s.charAt(i)
        i += 1
        r
    }
    
    def remaining = limit - i
    
    def dup = new ParStringSplitter_DIAG(s, i, limit)
    
    def split = {
        val rem = remaining
        if (rem >= 2) psplit(rem / 2, rem - rem / 2)
        else Seq(this)
    }
    
    def psplit(sizes: Int*): Seq[ParStringSplitter_DIAG] = {
        val ss = for (sz <- sizes) yield {
            val nlimit = (i + sz) min limit
            val ps = new ParStringSplitter_DIAG(s, i, nlimit)
            i = nlimit
            ps
        }
        if (i == limit) ss else ss :+ new ParStringSplitter_DIAG(s, i, limit)
    }
}


