package com.beaconrep.libalgo.apps

import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.DenseVector
import org.apache.spark.mllib.evaluation._
import org.apache.spark.sql.types.{StructType, StructField, IntegerType, LongType, StringType, DoubleType}
import org.apache.spark.mllib.classification.{NaiveBayes,NaiveBayesModel}

import org.apache.spark.mllib.linalg.{Vector, Vectors}


import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf

import org.apache.spark.mllib.tree._
import org.apache.spark.mllib.tree.model._

object NetworkAssetFingerPrinting_bk extends App {
  
  val NetworkDao =
   StructType(
     StructField("protocol", IntegerType, false) ::
     StructField("src_port", IntegerType, false) ::
     StructField("dst_port", IntegerType, false) ::
     StructField("protocol_id", IntegerType, false) ::
     Nil)
  
  val frameWork = SparkSession
  .builder()
  .appName("network-decision-tree")
  .master("local[*]")
  .getOrCreate()

  //Train & Test the Model
  
  val dataValues = frameWork.read.schema(NetworkDao).option("header","true").csv("resources/network/Network_train.csv")
  
  dataValues.createTempView("data")
  
  val dataValuesRDD = frameWork.sql("SELECT protocol,src_port,dst_port, protocol_id FROM data").rdd.map(
     row => {
      val rowValues = row.toSeq.toArray.map({
        case l:Long => l.toDouble
        case i:Int => i.toDouble
        case d:Double => d
        case null => 0.0
      })
       val featureVector = new DenseVector(rowValues.tail)
       val label = rowValues(0)
       LabeledPoint(label, featureVector)
     })
     
     
     
     val splits = dataValuesRDD.randomSplit(Array(0.7, 0.3))
     val (trainingData, test) = (splits(0), splits(1))
     val impurity = "entropy"
     val numClasses = 4
     val categoricalFeaturesInfo = Map[Int, Int]((3 -> 0),(2 -> 1), (2 -> 2), (2 -> 3))
     val maxDepth = 5
     val maxBins = 100
     val numTrees = 3
     val featureSubsetStrategy = "auto"
    val model = RandomForest.trainClassifier(trainingData, 
                                             numClasses, 
                                             categoricalFeaturesInfo, 
                                             numTrees,
                                             featureSubsetStrategy,
                                             impurity, 
                                             maxDepth, 
                                             maxBins) 
     
     val predictionAndLabel = test.map(p => (model.predict(p.features), p.label))
     predictionAndLabel.foreach(f => println(f))
     
     println(model.algo)
     
     println(model.predict(Vectors.dense(504, 0, 1)))
         
     println(model.predict(Vectors.dense(0, 504, 1)))
     
     println(model.predict(Vectors.dense(0, 0, 2)))
     
     println(model.predict(Vectors.dense(5, 0, 3)))
     
}