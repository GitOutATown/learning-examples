package org.learningconcurrency.ch4.lab

import scala.concurrent._
import ExecutionContext.Implicits.global
  
object Future_lab_1 {

  // Better see the App (non ws version!). The order is different.

  Future {
    println("the future is here")
  }                                               //> the future is here
                                                  //| res0: scala.concurrent.Future[Unit] = scala.concurrent.impl.Promise$DefaultP
                                                  //| romise@573c3ba2
  println("the future is coming")                 //> the future is coming
  Thread.sleep(1000)
  

  '''                                             //> res1: Char('\'') = '
}