package com.beaconrep.libalgo.apps

import com.beaconrep.libalgo.core._

object BeaconRepDecisionTree extends App {
  
  val frameWork = new SparkFramework("DecisionTree", "local[*]")
  
  val algorithm = new SparkDecisionTree(frameWork.getSession(),
                                        "/Users/VK/git/beaconrep/data/GeneratedData/Parameters_300.json")
  
}