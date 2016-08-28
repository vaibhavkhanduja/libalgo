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

class SparkDecisionTree(frameWork: SparkSession) extends  java.io.Serializable with algorithm  {
 
  private var dataValues:DataFrame = null
  
  private var dataValuesRDD: RDD[LabeledPoint] = null
  
  private var model: DecisionTreeModel = null
  
    
  
  def initCSVDataPoint(filePath:String) = {
      dataValues = frameWork.read.option("header","true").csv(filePath)
      
       dataValuesRDD = dataValues.rdd.map( 
           
         row => {
           val rowValues =  row.toString.slice(1, row.toString.length - 1).split(",").toArray.map({
               case s:String => s.toDouble
               
           })
           val featureVector = new DenseVector(rowValues.init)
           val label = rowValues(0)
           LabeledPoint(label, featureVector)
         })
  }
         
   def initJSONDataPoint(filePath:String) = {
       dataValues = frameWork.read.json(filePath)
       dataValuesRDD = dataValues.rdd.map( 
           row => {
             val rowValues = row.toSeq.toArray.map({
             case l:Long => l.toDouble
             })
           val featureVector = new DenseVector(rowValues.init)
           val label = rowValues(0)
           LabeledPoint(label, featureVector)
         })
      }
      
def buildModel: MulticlassMetrics = {
     val splits = dataValuesRDD.randomSplit(Array(0.7, 0.3))
     val (trainingData, testData) = (splits(0), splits(1))
     val impurity = "entropy"
     val numClasses = 2
     val categoricalFeaturesInfo = Map[Int, Int]()

     val maxDepth = 5
     val maxBins = 32

     model = DecisionTree.trainClassifier(trainingData, 
                                             numClasses, 
                                             categoricalFeaturesInfo, 
                                             impurity, 
                                             maxDepth, 
                                             maxBins) 
                                             
     getMetrics
    
  }
      
  
   def getMetrics:
    MulticlassMetrics = {
       val predictionsAndLabels = dataValuesRDD.map(example =>
         (model.predict(example.features), example.label)
      )
     new MulticlassMetrics(predictionsAndLabels)
     }
  }