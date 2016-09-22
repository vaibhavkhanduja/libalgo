package com.beaconrep.libalgo.apps


import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.sql.types.{StructType, StructField, IntegerType, LongType, StringType, DoubleType}

object NetworkFingerPrintingNaive extends App {
  
  val NetworkDao =
   StructType(
     StructField("protocol", IntegerType, false) ::
     StructField("src_port", IntegerType, false) ::
     StructField("dst_port", IntegerType, false) ::
     StructField("protocol_id", IntegerType, false) ::
     Nil)
     
  val frameWork = new SparkFramework("Naive", "local[*]")
  
  val featureString = new String("protocol,src_port,dst_port,protocol_id")
  
  val algorithm = new SparkNaiveBayes(frameWork.getSession())
  
  algorithm.initCSVDataPoint("resources/network/Network_train.csv", NetworkDao, featureString)
  
  algorithm.buildModel
           
  val dv: Vector = Vectors.dense(0, 504, 1)
  
  println(algorithm.predict(dv))

}