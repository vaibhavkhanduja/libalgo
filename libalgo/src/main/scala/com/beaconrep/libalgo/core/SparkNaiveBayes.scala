package com.beaconrep.libalgo.core

import com.beaconrep.libalgo._
import org.apache.spark.sql._
import org.apache.spark.rdd._
import org.apache.spark.mllib.evaluation._
import org.apache.spark.mllib._
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.DenseVector
import org.apache.spark.mllib.classification.{NaiveBayes,NaiveBayesModel}


class SparkNaiveBayes(INframeWork: SparkSession) extends algorithm {
  
  frameWork = INframeWork
  private var model: NaiveBayesModel = null 
      
def buildModel: MulticlassMetrics = {
  val splits = dataValuesRDD.randomSplit(Array(0.7, 0.3))
  val (trainingData, testData) = (splits(0), splits(1))
  model = NaiveBayes.train(trainingData, lambda = 1.0, "multinomial")
  getMetrics(model, dataValuesRDD)
}
    
def getMetrics(demodel: NaiveBayesModel, data:RDD[LabeledPoint]) :
    MulticlassMetrics = {
       val predictionsAndLabels = data.map(example =>
         (demodel.predict(example.features), example.label)
      )
     new MulticlassMetrics(predictionsAndLabels)
   }
}