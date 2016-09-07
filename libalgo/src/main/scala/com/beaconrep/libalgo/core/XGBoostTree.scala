package com.beaconrep.libalgo.core

import com.beaconrep.libalgo._
import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.DenseVector
import org.apache.spark.mllib.evaluation._
import org.apache.spark.rdd._
import org.apache.spark.sql._

//import ml.dmlc.xgboost4j.scala.XGBoost

import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.tree.model.RandomForestModel

class XGBoostTree(INframeWork:SparkSession) extends algorithm {
  
  frameWork = INframeWork
  
  def buildModel: MulticlassMetrics = {
   val splits = dataValuesRDD.randomSplit(Array(0.7, 0.3))
   val (trainingData, testData) = (splits(0), splits(1))
   val impurity = "entropy"
   val numClasses = 2
   val categoricalFeaturesInfo = Map[Int, Int]()
   val maxDepth = 5
   val maxBins = 100
   val numTrees = 3
   val featureSubsetStrategy = "auto"
  // val xgboostModel = XGBoost.train(trainingData, params.toMap, 2, 1)
   val model = RandomForest.trainClassifier(trainingData, 
                                             numClasses, 
                                             categoricalFeaturesInfo, 
                                             numTrees,
                                             featureSubsetStrategy,
                                             impurity, 
                                             maxDepth, 
                                             maxBins) 
   getMetrics(model, dataValuesRDD)
  }   
  
  def getMetrics(demodel: RandomForestModel, data:RDD[LabeledPoint]) :
    MulticlassMetrics = {
       val predictionsAndLabels = data.map(example =>
         (demodel.predict(example.features), example.label)
      )
     new MulticlassMetrics(predictionsAndLabels)
     }
}