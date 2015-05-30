package org.learningconcurrency.ch6.lab

import rx.lang.scala._
import org.apache.commons.io.monitor._
    
object ObservablesHot extends App {
    println("ObservablesHot TOP")

    val fileMonitor = new FileAlterationMonitor(1000)
    fileMonitor.start()

    def modifiedFiles(directory: String): Observable[String] = {
        println(s"modifiedFiles, directory: $directory")
        
        val fileObs = new FileAlterationObserver(directory)
        fileMonitor.addObserver(fileObs)
        
        // constructing new Observalble with observer
        Observable.apply { observer =>
            println("==>Observable.apply, observer: " + observer)
            val fileLis = new FileAlterationListenerAdaptor {
                override def onFileChange(file: java.io.File) {
                    observer.onNext(file.getName)
                }
            }
            fileObs.addListener(fileLis)

            Subscription {
                // What is this supposed to do? This is not a def for unsubscribe.
                fileObs.removeListener(fileLis)
            }
        }
    }

    println(s"first subscribe call")
    val subscription1 = modifiedFiles(".").subscribe(filename => 
            println(s"$filename subscription1 modified!"))

    Thread.sleep(10000)

    println(s"another subscribe call")
    val subscription2 = modifiedFiles(".").subscribe(filename => 
            println(s"$filename subscription2 changed!"))

    Thread.sleep(10000)

    println(s"unsubscribed second call")
    // What is the purpose? What about subscrition1 unsubscribe?
    subscription2.unsubscribe()
    Thread.sleep(10000)

    fileMonitor.stop()
    println("END")

}