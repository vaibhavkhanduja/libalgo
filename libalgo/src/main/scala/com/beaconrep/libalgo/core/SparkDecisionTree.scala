package com.beaconrep.libalgo.core

import com.beaconrep.libalgo._
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.mllib._
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.DenseVector
import org.apache.spark.mllib.tree.DecisionTree

class SparkDecisionTree(frameWork: SparkSession, filePath:String) extends algorithm {
  
  val dataValues = frameWork.read.json(filePath)
  
  dataValues.show()
  
   val dataValuesRDD: RDD[LabeledPoint] = dataValues.rdd.map( 
         row => { LabeledPoint(row.getLong(0),
                               new DenseVector(Array(row.getLong(1),
                                                     row.getLong(2), 
                                                     row.getLong(3), 
                                                     row.getLong(4), 
                                                     row.getLong(5))))
                  }
             )
  
   val splits = dataValuesRDD.randomSplit(Array(0.7, 0.3))
   val (trainingData, testData) = (splits(0), splits(1))
   val impurity = "entropy"
   val numClasses = 2
   val categoricalFeaturesInfo = Map[Int, Int]()

   val maxDepth = 5
   val maxBins = 32

   val model = DecisionTree.trainClassifier(trainingData, 
                                             numClasses, 
                                             categoricalFeaturesInfo, 
                                             impurity, 
                                             maxDepth, 
                                             maxBins)                                  
}