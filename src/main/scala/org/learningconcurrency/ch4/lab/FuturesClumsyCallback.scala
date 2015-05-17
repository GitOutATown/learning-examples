package org.learningconcurrency.ch4.lab

import scala.io.Source
import java.io._
import org.apache.commons.io.FileUtils._
import scala.collection.convert.decorateAsScala._
import scala.concurrent._
import ExecutionContext.Implicits.global

object FuturesClumsyCallback extends App {
    
    // This is just an example from the book of what not to do so I've 
    // abandoned the implementation.
    
    def blacklistFile(name: String): Future[List[String]] = Future {
        val lines = Source.fromFile(name).getLines
        lines.filter(line => !line.startsWith("#") && !line.isEmpty).toList
    }
}