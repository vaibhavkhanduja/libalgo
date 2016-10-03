package com.beaconrep.libalgo.core

import com.beaconrep.libalgo._
import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.DenseVector
import org.apache.spark.mllib.evaluation._
import org.apache.spark.rdd._
import org.apache.spark.sql._
import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.tree.model.RandomForestModel
import org.apache.spark.mllib.linalg.{Vector}

class SparkRandomForest(INframeWork:SparkSession, INdata:facts) extends algorithm {
  
   frameWork = INframeWork
   private val data = INdata
   private var model: RandomForestModel = null 
 
      
def buildModel: BinaryClassificationMetrics = {
  val splits = data.getSplits(0.7, 0.3)
  val (trainingData, testData) = (splits(0), splits(1))
  val impurity = "entropy"
  val numClasses = 2
  val categoricalFeaturesInfo = Map[Int, Int]()
  val maxDepth = 5
  val maxBins = 100
  val numTrees = 3
  val featureSubsetStrategy = "auto"
  model = RandomForest.trainClassifier(trainingData, 
                                             numClasses, 
                                             categoricalFeaturesInfo, 
                                             numTrees,
                                             featureSubsetStrategy,
                                             impurity, 
                                             maxDepth, 
                                             maxBins) 
   getMetrics(model, testData)
 }     
  
  
def getMetrics(demodel: RandomForestModel, data:RDD[LabeledPoint]) :
  BinaryClassificationMetrics = {
    val predictionsAndLabels = data.map(example =>
       (demodel.predict(example.features), example.label)
    )
   new BinaryClassificationMetrics(predictionsAndLabels)
  }

def predict(predictVector:Vector):Double = {
   model.predict(predictVector) 
 }
}