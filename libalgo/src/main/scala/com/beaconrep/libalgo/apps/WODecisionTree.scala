package com.beaconrep.libalgo.apps

import com.beaconrep.libalgo.core._

//import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

object WODecisionTree extends App {
  
  val frameWork = new SparkFramework("DecisionTree", "local[*]")
  
  val algorithm = new SparkDecisionTree(frameWork.getSession())
  
  algorithm.initCSVDataPoint("resources/ParameterMaterialNoEmpty.csv")
           
  println(algorithm.buildModel.scoreAndLabels);
  
}