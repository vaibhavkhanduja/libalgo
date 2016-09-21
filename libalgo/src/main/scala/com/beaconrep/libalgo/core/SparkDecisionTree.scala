package com.beaconrep.libalgo.core

import com.beaconrep.libalgo._
import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.DenseVector
import org.apache.spark.mllib.evaluation._
import org.apache.spark.mllib.tree._
import org.apache.spark.mllib.tree.model._
import org.apache.spark.rdd._
import org.apache.spark.sql._

import org.apache.spark.sql.types.{StructType, StructField, IntegerType, LongType, StringType, DoubleType}


class SparkDecisionTree(INframeWork: SparkSession)  extends algorithm  {
 
  frameWork = INframeWork
  
  private var model: DecisionTreeModel = null 
 
      
def buildModel: BinaryClassificationMetrics = {
     val splits = dataValuesRDD.randomSplit(Array(0.7, 0.3))
     val (trainingData, testData) = (splits(0), splits(1))
     val impurity = "entropy"
     val numClasses = 2
     val categoricalFeaturesInfo = Map[Int, Int]()

     val maxDepth = 5
     val maxBins = 100

     model = DecisionTree.trainClassifier(trainingData, 
                                             numClasses, 
                                             categoricalFeaturesInfo, 
                                             impurity, 
                                             maxDepth, 
                                             maxBins) 
                                             
     getMetrics(model, testData)
    
  }
      
  
  def getMetrics(demodel: DecisionTreeModel, data:RDD[LabeledPoint]) :
    BinaryClassificationMetrics = {
       val predictionsAndLabels = data.map(example =>
         (demodel.predict(example.features), example.label)
      )
     new BinaryClassificationMetrics(predictionsAndLabels)
     }
  
 }