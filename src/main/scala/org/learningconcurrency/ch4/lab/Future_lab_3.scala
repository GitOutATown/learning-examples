package org.learningconcurrency.ch4.lab

import scala.concurrent._
import ExecutionContext.Implicits.global

object Future_lab_3 {
    val fut1 = Future // object Future, Future companion object, singleton
    val fut2 = Future { 1 } // def apply in Future companion object
}