package com.beaconrep.libalgo.apps

import com.beaconrep.libalgo.core._
import com.beaconrep.libalgo._

//import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

object WODecisionTree extends App {
  
  val frameWork = new SparkFramework("DecisionTree", "local[*]")
  
  val data = new facts(frameWork.getSession())
 
  data.initCSVDataPoint("resources/ParameterMaterialNoEmpty.csv")
  
  val algorithm = new SparkDecisionTree(frameWork.getSession(), data)
           
  println(algorithm.buildModel.scoreAndLabels);
  
}