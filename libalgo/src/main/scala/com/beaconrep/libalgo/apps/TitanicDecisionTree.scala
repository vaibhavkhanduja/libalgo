package com.beaconrep.libalgo.apps

import com.beaconrep.libalgo.core._

object TitanicDecsionTree extends App {
  
  final case class Titanic (id:Int)
  
  val frameWork = new SparkFramework("DecisionTree", "local[*]")
  
  val algorithm = new SparkDecisionTree(frameWork.getSession())
  
  algorithm.initCSVDataPoint("resources/titanic/train.csv", Titanic, 1)
           
  //println(algorithm.buildModel.scoreAndLabels);
  
}