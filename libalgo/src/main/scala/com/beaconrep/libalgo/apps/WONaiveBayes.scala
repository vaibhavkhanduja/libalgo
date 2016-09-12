package com.beaconrep.libalgo.apps

import com.beaconrep.libalgo.core._

object WONaiveBayes extends App {
  
  val frameWork = new SparkFramework("DecisionTree", "local[*]")
  
  val algorithm = new SparkNaiveBayes(frameWork.getSession())
  
  algorithm.initCSVDataPoint("resources/ParameterMaterialNoEmpty.csv")
           
  println(algorithm.buildModel.scoreAndLabels)

  
}