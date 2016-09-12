package com.beaconrep.libalgo.apps

import com.beaconrep.libalgo.core._

object WORandomForest extends App {
  
  val frameWork = new SparkFramework("RandomForest", "local[*]")
  
  val algorithm = new SparkRandomForest(frameWork.getSession())
  
  algorithm.initCSVDataPoint("resources/ParameterMaterialNoEmpty.csv")
           
  algorithm.buildModel.scoreAndLabels.foreach(f => println(f._1, f._2))
  
}