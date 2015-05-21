package org.learningconcurrency.ch5.lab

object Palindrome {

  val startTime = System.currentTimeMillis        //> startTime  : Long = 1432101126810
  // took 352, 341, 349
  //(0 until 100000).filter(x => x.toString == x.toString.reverse).length
  
  // took 525, 489, 609, 549 -- No time savings here!!
  (0 until 100000).par.filter(x => x.toString == x.toString.reverse).length
                                                  //> res0: Int = 1099
  val completedTime = System.currentTimeMillis    //> completedTime  : Long = 1432101127312
  
  completedTime - startTime                       //> res1: Long = 502
  '''                                             //> res2: Char('\'') = '
}