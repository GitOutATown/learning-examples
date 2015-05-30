package org.learningconcurrency.ch6.lab

import java.util.{Timer, TimerTask}
import scala.collection._
import rx.lang.scala._
    
object ObservablesHotVsCold extends App {

    val songs = List("Eye of the Tiger", "You Spin Me Round", "Rebel Yell")
    val myPlaylist = Observable.from(songs)

    object Player extends TimerTask {
        val timer = new Timer
        var index = 0
        var subscribers = mutable.Set[Subscriber[String]]()
        def start() = timer.schedule(this, 0L, 1000L)
        def stop() = timer.cancel()

        def run() {
            index = (index + 1) % songs.length
            Player.synchronized { for (s <- subscribers) s.onNext(songs(index)) }
        }
        def turnOn(s: Subscriber[String]) = Player.synchronized { subscribers += s }
        def turnOff(s: Subscriber[String]) = Player.synchronized { subscribers -= s }
    }
    Player.start()

    val currentlyPlaying = Observable[String] { subscriber =>
        Player.turnOn(subscriber)
        subscriber.add(Subscription { Player.turnOff(subscriber) })
    }

    println(s"adding to a cold observable")
    myPlaylist.subscribe(println(_))
    println(s"adding to a cold observable again")
    myPlaylist.subscribe(println(_))
    Thread.sleep(2000)

    println(s"adding to a hot observable")
    val subscription1 = currentlyPlaying.subscribe(println(_))
    Thread.sleep(2400)
    subscription1.unsubscribe()
    Thread.sleep(1200)
    println(s"adding to a hot observable again")
    val subscription2 = currentlyPlaying.subscribe(println(_))
    Thread.sleep(2400)
    subscription2.unsubscribe()
    Thread.sleep(1200)
    println(s"Done -- shutting down the Player")
    Player.stop()

}