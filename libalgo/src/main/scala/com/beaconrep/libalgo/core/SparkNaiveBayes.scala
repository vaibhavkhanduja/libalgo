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
import org.apache.spark.mllib.linalg.{Vector}



class SparkNaiveBayes(INframeWork: SparkSession,  INdata:facts) extends algorithm {
  
  frameWork = INframeWork
  private val data = INdata
  private var model: NaiveBayesModel = null 
      
def buildModel: BinaryClassificationMetrics = {
  val splits = data.getSplits(0.7, 0.3)
  val (trainingData, testData) = (splits(0), splits(1))
  model = NaiveBayes.train(trainingData, lambda = 1.0, "multinomial")
  getMetrics(model, testData)
}
    
def getMetrics(demodel: NaiveBayesModel, data:RDD[LabeledPoint]) :
    BinaryClassificationMetrics = {
       val predictionsAndLabels = data.map(example =>
         (demodel.predict(example.features), example.label)
      )
     new BinaryClassificationMetrics(predictionsAndLabels)
   }

def predict(predictfeatureData: Vector): Double = {
  
   println(model.predictProbabilities(predictfeatureData).toJson)
   model.predict(predictfeatureData)
 }

}

 