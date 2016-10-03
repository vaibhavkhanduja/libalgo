package com.beaconrep.libalgo

import org.apache.spark.mllib.evaluation._
import org.apache.spark.rdd._
import org.apache.spark.sql._
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.DenseVector

import org.apache.spark.mllib.linalg.{Vector}

import org.apache.spark.sql.types.{StructType}


trait algorithm {
   
   protected var frameWork: SparkSession = null
  
   def predict(testData: Vector): Double
   
}