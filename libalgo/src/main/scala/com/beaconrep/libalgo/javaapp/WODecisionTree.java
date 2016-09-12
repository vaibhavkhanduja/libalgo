package com.beaconrep.libalgo.javaapp;

import com.beaconrep.libalgo.algorithm;
import com.beaconrep.libalgo.core.*;

public class WODecisionTree {

  public static void main(String[] args) {
    SparkFramework frameWork = new SparkFramework("RandomForest", "local[*]");    
    algorithm our_algorithm = new SparkRandomForest(frameWork.getSession());
    our_algorithm.initCSVDataPoint("resources/ParameterMaterialNoEmpty.csv");
    //System.out.println(our_algorithm.buildModel().
  }
}
