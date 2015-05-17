package org.learningconcurrency.ch4.lab

import scala.concurrent._
import ExecutionContext.Implicits.global

object PromisesCancellation_2 extends App {

  def cancellable[T](b: Future[Unit] => T): (Promise[Unit], Future[T]) = {
    val p = Promise[Unit]
    val f = Future {
      val returnValue = b(p.future)
      if (!p.tryFailure(new Exception)) throw new CancellationException
      returnValue
    }
    (p, f)
  }

  val (cancel_P, value) = cancellable { cancel_F =>
    var i = 0
    while (i < 5) {
      if (cancel_F.isCompleted) throw new CancellationException
      Thread.sleep(500)
      println(s"$i: working")
      i += 1
    }
    val result = "resulting value"
    result
  }

  Thread.sleep(1500)

  cancel_P trySuccess ()

  println("computation cancelled!")
}

