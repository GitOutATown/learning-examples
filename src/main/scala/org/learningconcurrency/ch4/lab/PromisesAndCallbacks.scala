package org.learningconcurrency.ch4.lab

import scala.concurrent._
import ExecutionContext.Implicits.global
import org.apache.commons.io.monitor._
import java.io.File
import scala.util.{Failure, Success, Try}

object PromisesAndCallbacks extends App {

  // This is screwy. Only works on code compile cause by code change!
   
    
  def fileCreated(directory: String): Future[String] = {
    println("~~In fileCreated, directory: " + directory)
    val p = Promise[String]

    val fileMonitor = new FileAlterationMonitor(1000)
    val observer = new FileAlterationObserver(directory)
    val listener = new FileAlterationListenerAdaptor {
      override def onFileCreate(file: File) {
        println("~~In onFileCreate")
        try p.trySuccess(file.getName)
        finally fileMonitor.stop()
      }
    }
    observer.addListener(listener)
    fileMonitor.addObserver(observer)
    fileMonitor.start()
    p.future
  }

  fileCreated(".") onComplete {
    case Success(filename) => println(s"Detected new file '$filename'")
    case Failure(e) => println("WTF?" + e)
  }
  
  println("TCB")
  Thread.sleep(3000)
  println("JVM leavin da haus")
}

