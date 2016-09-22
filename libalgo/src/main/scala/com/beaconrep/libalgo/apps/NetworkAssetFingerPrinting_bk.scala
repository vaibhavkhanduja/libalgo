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
     
     val Array(training, test) = dataValuesRDD.randomSplit(Array(0.6, 0.4))
     
     
     val impurity = "entropy"
     val numClasses = 6
     val categoricalFeaturesInfo = Map[Int, Int]()

     val maxDepth = 5
     val maxBins = 100

    val model = DecisionTree.trainClassifier(training, 
                                             numClasses, 
                                             categoricalFeaturesInfo, 
                                             impurity, 
                                             maxDepth, 
                                             maxBins) 
                                             
                                           
    // val model = NaiveBayes.train(training, lambda = 1.0, "multinomial")
     
     val predictionAndLabel = test.map(p => (model.predict(p.features), p.label))
     predictionAndLabel.foreach(f => println(f))
     
     //Predict with another random data
     
     val dataValues_p = frameWork.read.schema(NetworkDao).option("header","true").csv("resources/network/Network_predict.csv")
  
    dataValues_p.createTempView("data_p")
   
  
    val dataValuesRDD_p = frameWork.sql("SELECT protocol,src_port,dst_port, protocol_id FROM data_p").rdd.map(
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
     
     val predictionAndLabel_p = dataValuesRDD_p.map(p => (model.predict(p.features), p.label))
     predictionAndLabel_p.foreach(f => println(f))
     
     val dv: Vector = Vectors.dense(0, 504, 1)
     
     println(model.predict(dv))
    
  
}