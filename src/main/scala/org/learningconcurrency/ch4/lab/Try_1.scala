package org.learningconcurrency.ch4.lab

import scala.util.{Try, Success, Failure}
   
object Try_1 extends App {
    
    val threadName: Try[String] = Try(Thread.currentThread.getName)
    val someText: Try[String] = Try("Try objects are synchronous")
    
    val message: Try[String] = for {
        tn <- threadName // flatMap
        st <- someText // map
    } yield s"Message $st was created on thread = $tn"
    
    def handleMessage(msg: Try[String]) {
        msg match {
            case Success(msg) => println("message: " + msg)
            case Failure(e) => println("exception: " + e)
        }
    }
 
    handleMessage(message)
}