package com.beaconrep.libalgo.apps

import com.beaconrep.libalgo.core._

object WODecisionTree extends App {
  
  val frameWork = new SparkFramework("DecisionTree", "local[*]")
  
  val algorithm = new SparkDecisionTree(frameWork.getSession())
  
  algorithm.initCSVDataPoint("resources/ParameterMaterialNoEmpty.csv")
           
  println(algorithm.buildModel.confusionMatrix)
}