package org.learningconcurrency.ch6.lab

import rx.lang.scala._
import org.apache.commons.io.monitor._
    
object ObservablesSubscriptions extends App {

    def modifiedFiles(directory: String): Observable[String] = {
        println(s"def modifiedFiles, directory: $directory")
        Observable.apply { observer =>
            val fileMonitor = new FileAlterationMonitor(1000)
            val fileObs = new FileAlterationObserver(directory)
            val fileLis = new FileAlterationListenerAdaptor {
                override def onFileChange(file: java.io.File) {
                    observer.onNext(file.getName)
                }
            }
            fileObs.addListener(fileLis)
            fileMonitor.addObserver(fileObs)
            fileMonitor.start()

            val subsc = Subscription {
                // What is this supposed to do? This is not a def for unsubscribe.
                fileMonitor.stop()
            }
            println("subsc: " + subsc)
            subsc
        }
    }

    println(s"starting to monitor files")
    val subscription = modifiedFiles(".").subscribe(filename => println(s"$filename modified!"))
    println(s"please modify and save a file")

    Thread.sleep(10000)

    // THIS IS NOT WORKING, It's unsubscribing, but the fileMonitor is not stopping.
    // Subscription is not getting constructed.
    subscription.unsubscribe() 
    Thread.sleep(1500)
    println("unsubscribed? " + subscription.isUnsubscribed)
    println(s"monitoring done -- Nope, doesn't appear to be stopping...")
}

