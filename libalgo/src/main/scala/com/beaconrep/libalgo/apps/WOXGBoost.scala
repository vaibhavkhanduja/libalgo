package com.beaconrep.libalgo.apps

import com.beaconrep.libalgo.core._
import com.beaconrep.libalgo._

object WOXGBoost extends App {
  
  val frameWork = new SparkFramework("DecisionTree", "local[*]")
  
  val data = new facts(frameWork.getSession())
  
  data.initCSVDataPoint("resources/ParameterMaterialNoEmpty.csv")
  
  val algorithm = new XGBoostTree(frameWork.getSession(), data)
  
  algorithm.buildModel
  
}