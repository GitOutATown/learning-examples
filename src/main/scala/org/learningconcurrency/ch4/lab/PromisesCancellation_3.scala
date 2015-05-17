package org.learningconcurrency.ch4.lab

import scala.concurrent._
import ExecutionContext.Implicits.global

object PromisesCancellation_3 extends App {

   /*
     * We are passing an anonymous function to runContext. This anonymous 
     * function contains in its body the anynchronous computation that will 
     * return a 'value'. Note the familiar pattern in which a val is used to 
     * hold a reference to a working Future returned from a factory (context) 
     * that also provides reference to a paired Promise (i.e. tuple) that 
     * provides the two way communication between the asynchronous computation 
     * and the client for cancellation. This means that the client will receive 
     * notice of the completed async computation, and the working Future (i.e. 
     * anynchronous computation) will recieve notice if client wants it to 
     * cancel the async computation.
     * 
     * This anonymous function passed to runContext will be referred to as
     * 'body' there (because the body is in fact concrete executable code
     * whereas the input is just a placeholder of an arg that will be passed
     * to 'body' inside runContext. When invoked, this input to 'body', 
     * cancel_F, will be substituted with cancel_P.future. cancel_P is a 
     * Promise[Unit]. cancel_P.future is the context for cancellation. If it
     * is completed by the client calling ...
     * 
     * cancel_P and cancel_F both refer to the same Promise/Future duality.
     * The client uses the cancel_P Promise to cancel the async computation, 
     * and the async computation (working Future) checks cancel_F to see
     * if it should throw a CancellationException.
     */
    val (cancel_P, value) = runContext { cancel_F => 
        { ///// body that will be ansychronous computation in cancellable
            val result = for {
                 i <- 1 to 5
            } yield {
                 Thread.sleep(500)
                 println(s"$i: working")
                 if (cancel_F.isCompleted) throw new CancellationException
                 i
            }
            result.toList // List[Int]
        } ///// end body
    }
    
    /* runContext is renamed from cancellable in the book (p. 126/149).
     * The body of the 'body' param is the asynchronous computation. It will
     * commence when 'body' function is called on cancel_P.future.
     */
    def runContext[T](body: Future[Unit] => T): (Promise[Unit], Future[T]) = {
        val cancel_P = Promise[Unit]
        val asyncCompFut = Future {
            val returnValue = body(cancel_P.future)
            if (!cancel_P.tryFailure(new Exception)) throw new CancellationException
            returnValue // completed type T
        }
        (cancel_P, asyncCompFut)
    }

    value foreach { case v => println("value: " + v) }
    value.failed foreach { case e => println("error: " + e) }
    
    // Simulated cancellation period threshhold
    Thread.sleep(scala.util.Random.nextInt(7000))
    cancel_P trySuccess () // 'cancel' Promise becomes completed

    Thread.sleep(2500)
    println("JVM leavin da haus")
}

