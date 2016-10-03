package com.beaconrep.libalgo

import org.apache.spark.mllib.evaluation._
import org.apache.spark.rdd._
import org.apache.spark.sql._
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.DenseVector

import org.apache.spark.mllib.linalg.{Vector}

import org.apache.spark.sql.types.{StructType}


class facts(INframeWork: SparkSession) {
  
  protected var dataValues:DataFrame = null
  
   protected var dataValuesRDD: RDD[LabeledPoint] = null
   
   protected var frameWork: SparkSession = INframeWork
 
   
   def initCSVDataPoint(filePath:String, labelCol:Int = 0) = {
     
      dataValues = frameWork.read.option("header","true").option("inferSchema", "true").csv(filePath)
      
       dataValuesRDD = dataValues.rdd.map(
           
         row => {
           val rowValues = row.toSeq.toArray.map({
               case s:String =>
                 if (("[".equals(s)) || ("]".equals(s)))  
                   0.0
                 else s.toDouble
               case l:Long => l.toDouble
               case i:Int => i.toInt
               case d:Double => d
               case null => 0.0
           })
           val featureVector = new DenseVector(rowValues.tail)
           val label = rowValues(labelCol)
           LabeledPoint(label, featureVector)
         })
  }
   

   def initCSVDataPoint(filePath:String, dao:StructType,featureQuery:String) = {
     
     dataValues = frameWork.read.schema(dao).option("header", "true").csv(filePath)
     
     dataValues.createTempView("data")
     
     dataValuesRDD = frameWork.sql("SELECT " + featureQuery + " FROM data").rdd.map(
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
     }
     
   def initJSONDataPoint(filePath:String) = {
       dataValues = frameWork.read.json(filePath)
       dataValuesRDD = dataValues.rdd.map( 
           row => {
             val rowValues = row.toSeq.toArray.map({
             case l:Long => l.toDouble
             })
           val featureVector = new DenseVector(rowValues.tail)
           val label = rowValues(0)
           LabeledPoint(label, featureVector)
         })
      }
   
   def getSplits(train:Double, test:Double) = {
     dataValuesRDD.randomSplit(Array(train, test))
   }
 
}