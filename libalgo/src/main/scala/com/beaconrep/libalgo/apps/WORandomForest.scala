package com.beaconrep.libalgo.apps

import com.beaconrep.libalgo.core._

object WORandomForest {
  
  val frameWork = new SparkFramework("DecisionTree", "local[*]")
  
  val algorithm = new SparkRandomForest(frameWork.getSession())
  
  algorithm.initCSVDataPoint("resources/ParameterMaterialNoEmpty.csv")
           
  println(algorithm.buildModel.confusionMatrix)
  
  println(algorithm.buildModel.accuracy)
  
  println(algorithm.buildModel.precision(0.0))
  
}