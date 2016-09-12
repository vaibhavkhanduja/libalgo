package com.beaconrep.libalgo.core

import com.beaconrep.libalgo._
import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.evaluation._
import org.apache.spark.rdd._
import org.apache.spark.sql._
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.linalg.DenseVector

//import ml.dmlc.xgboost4j.scala.spark.{XGBoost, XGBoostModel}

import ml.dmlc.xgboost4j.scala.DMatrix
import ml.dmlc.xgboost4j.scala.XGBoost


class XGBoostTree(INframeWork:SparkSession) extends algorithm {
  
  frameWork = INframeWork
  //var model:XGBoostModel = null
  
 /* def buildModel {
   val splits = dataValuesRDD.randomSplit(Array(0.7, 0.3))
   val (trainingData, testData) = (splits(0), splits(1))
   
   val paramMap = List(
       "eta" -> 0.1f,
       "max_depth" -> 2,
       "objective" -> "binary:logistic").toMap
       
  println("Started to train model")
       
   model = XGBoost.train(trainingData, paramMap.toMap,  2, nWorkers = 4, useExternalMemory = true)
   
   println("Model created")
   
   val v0 = new DenseVector(Array(1,2,3))
   
   val pridictValues = INframeWork.sparkContext.parallelize(Seq(v0))
   
   val prediction = model.predict(pridictValues, 0)
   
   model.eval(testData, null, "error", false)
   
   prediction.foreach { x => println(x) }
      
  } */
  
  def buildModel {
  
   val trainData = new DMatrix("resources/ParameterMaterialNoEmpty.csv")
    // define parameters
    val paramMap = List(
      "eta" -> 0.1,
      "max_depth" -> 2,
      "objective" -> "binary:logistic").toMap
    // number of iterations
    val round = 2
    // train the model
    val model = XGBoost.train(trainData, paramMap, round)
    // run prediction
    val predTrain = model.predict(trainData)
    
    predTrain.foreach { x => println(x) }
 }
  
}