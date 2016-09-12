package com.beaconrep.libalgo.apps

import com.beaconrep.libalgo.core._

object WOXGBoost extends App {
  
  val frameWork = new SparkFramework("DecisionTree", "local[*]")
  
  val algorithm = new XGBoostTree(frameWork.getSession())
  
  algorithm.initCSVDataPoint("resources/ParameterMaterialNoEmpty.csv")
  
  algorithm.buildModel
  
 //val frameWork =  new XGBoostTree("resources/ParameterMaterialNoEmpty.csv")
  
}