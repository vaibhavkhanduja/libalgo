package com.beaconrep.libalgo.core

import com.beaconrep.libalgo._

import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.evaluation._
import org.apache.spark.rdd._
import org.apache.spark.sql._
import org.apache.spark.mllib.linalg.{Vectors, Vector}
import org.apache.spark.mllib.linalg.DenseVector


import ml.dmlc.xgboost4j.scala.spark.{XGBoost, XGBoostModel}

//import ml.dmlc.xgboost4j.scala.DMatrix
//import ml.dmlc.xgboost4j.scala.XGBoost


class XGBoostTree(INframeWork:SparkSession, INdata:facts) extends algorithm {
  
  frameWork = INframeWork
  val data = INdata
  var model:XGBoostModel = null
  
 def buildModel {
   val splits = data.getSplits(0.7, 0.3)
   val (trainingData, testData) = (splits(0), splits(1))
   
   val paramMap = List(
       "eta" -> 0.1f,
       "max_depth" -> 2,
       "objective" -> "binary:logistic").toMap
       
   model = XGBoost.trainWithRDD(trainingData, paramMap.toMap,  2, 4)
   
   println("Model created")
 
   
   println(model.eval(testData, new XGBoostEvaluator, "XGBoostEvaluator"))
      
}
  
  def predict(predicVector:Vector): Double = 0.0
}
