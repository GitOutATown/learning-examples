package org.learningconcurrency.ch4.lab

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.io.Source

object Netiquette_combinators extends App {
    // Comparing flatmap, map in chain (like desuggared for-comprehension)
    // with proper for-comprehension

    val netiquetteUrl = "http://www.ietf.org/rfc/rfc1855.txt"
    val netiquette = Future { Source.fromURL(netiquetteUrl).mkString }
    
    val urlSpecUrl = "http://www.w3.org/Addressing/URL/url-spec.txt"
    val urlSpec = Future { Source.fromURL(urlSpecUrl).mkString }
    
    // The non-elegant, hard to read, explicit calls to flatMap and map
    /* flatMap creates a new future by applying a function to the successful 
     * result of this future, and returns the result of the function as the 
     * new future. If this future is completed with an exception then the new 
     * future will also contain this exception.
     */
    val answer1 = netiquette.flatMap { nettext =>
        urlSpec.map { urltext =>
            "Check this out: " + nettext + ".\n\nAnd check out:\n" + urltext
        } 
    }
    answer1 foreach { case contents => println("answer1 contents:\n" + contents) }
    
    /* More elegant, easy to read, implicit flatMap and map.
     * Note that in this and the previous example the Futures
     * have been allowed to run their computation in parallel.
     * It is only the combinatorial call (flatMap) that there
     * is a dependency.
     */
    val answer2 = for {
        nettext <- netiquette // flatMap
        urltext <- urlSpec    // map
    } yield ("Check this out: " + nettext + ".\n\nAnd check out:\n" + urltext)
    answer2 foreach { case contents => println("answer2 contents:\n" + contents) }

    /* This example shows a computational dependency where the second Future
     * cannot start its computation until the First one completes. i.e. no
     * parallel computation.
     */
    val answer = for {
       nettext <- Future { Source.fromURL(netiquetteUrl).mkString }
       urltext <- Future { Source.fromURL(urlSpecUrl).mkString }
    } yield ("First, read this: " + nettext + ". Now, try this: " + urltext)
    answer foreach { case contents => println("answer contents:\n" + contents) }
    
    println("TCB")
    Thread.sleep(2000)
    println("END")
}




