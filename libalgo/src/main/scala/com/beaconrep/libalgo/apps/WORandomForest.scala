package com.beaconrep.libalgo.apps

import com.beaconrep.libalgo.core._
import com.beaconrep.libalgo._

object WORandomForest extends App {
  
  val frameWork = new SparkFramework("RandomForest", "local[*]")
 
  val data = new facts(frameWork.getSession())
  
  data.initCSVDataPoint("resources/ParameterMaterialNoEmpty.csv")
  
  val algorithm = new SparkRandomForest(frameWork.getSession(), data)
           
  algorithm.buildModel.scoreAndLabels.foreach(f => println(f._1, f._2))
  
}