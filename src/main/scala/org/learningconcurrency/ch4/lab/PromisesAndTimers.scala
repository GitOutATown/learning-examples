package org.learningconcurrency.ch4.lab

import scala.concurrent._
import ExecutionContext.Implicits.global

import java.util._
import scala.concurrent._
import ExecutionContext.Implicits.global
import PromisesAndCustomOperations._ // needed for def or
  
object PromisesAndTimers extends App {

  private val timer = new Timer(true) // Java
  
  /* TODO: Document/explain the methods and workings of this App.
   * This seems to relate to several things introduced in the Reactive
   * course:
   *   * priority queues ~ Study how the Java Timer is implemented.
   *   * timeout mechanism ~ I have yet to solve the timeout issue that
   *     Kept me from successfully completing (10/10) the week 3 assignment.
   */
  
  // Future with Java timer delay
  def timeout(millis: Long): Future[Unit] = {
    println("In timeout  TOP, millis: " + millis)
    val p = Promise[Unit]
    timer.schedule(new TimerTask {
      def run() = {
          println("In timeout run") // implementation of public abstract void run()
          p success () // timer task is run function
      }
    }, millis) // millis is duration
    p.future
  }

  println("declaring fut1 (in PromisesAndTimers)")
  // Composing default timeout with def 'or'
  val fut1 = timeout(1000).map(_ => "\'self\' timeout!") or Future {
    println("ThreadSleep_fut1 body block/apply/construction, computation begining")
    Thread.sleep(999)
    "ThreadSleep_fut1 work completed!"
  }
  println("fut1 val visible: " + fut1)

  println("declaring fut1 foreach callback...")
  // callback
  fut1 foreach {
    case text => println("====>>fut1 completed with: " + text)
  }
  
  println("PromisesAndTimers BOTTOM (before sleep and leavin...)")
  Thread.sleep(3000)
  println("JVM leavin da haus!")
} // end PromisesAndTimers

object PromisesAndCustomOperations { //extends App {
  import scala.concurrent._
  import scala.concurrent.Future
  import ExecutionContext.Implicits.global

  // called (constructed) each time def or is invoked
  implicit class FutureOps[T](val or_self: Future[T]) {
    println("In implicit class FutureOps constructor/apply")
    // or_self is timeout Future; or_that is Thread.sleep Future
    def or(or_that: Future[T]): Future[T] = {
      /*
       * In the constructor of an anon Future that registers the callbacks 
       * for or_self and or_that, either of which will invoke Promise p with 
       * tryComplete when callback occures.
       */
      println("In FutureOps def or TOP")
      val p = Promise[T] // new p with each call to def or
      println("In FutureOps def or p: " + p)
      // or_self is timeout Future. 
      or_self onComplete { case x => {
          println("In FutureOps def or or_self onComplete, x: " + x)
          p tryComplete x 
      }}
      // or_that is Thread.sleep Future; should win
      or_that onComplete { case y => {
          println("In FutureOps def or or_that onComplete, y: " + y)
          p tryComplete y }
      }
      println("Returning p.future from FutureOps def or")
      p.future
    } // end def or
  } // end implicit class FutureOps

  println("declaring fut2 (in PromisesAndCustomOperations)")
  /* I'm thinking that the order/race between these might be non-deterministic
   * and in fact I'm seeing evidence of that. */
  // Composing default timeout with def 'or'
  val fut2 = Future { "now" } or Future { "later" }
  println("val fut2 visible")
  
  println("declaring fut2 foreach callback...")
  // callback
  fut2 foreach {
    case when => println(s"====>>The fut2 future is $when")
  }
  
  println("PromisesAndCustomOperations BOTTOM...")
} // end PromisesAndCustomOperations


