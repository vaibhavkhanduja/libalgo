package com.beaconrep.libalgo

import org.apache.spark.mllib.evaluation._
import org.apache.spark.rdd._
import org.apache.spark.sql._
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.DenseVector

trait algorithm {
  
   protected var dataValues:DataFrame = null
  
   protected var dataValuesRDD: RDD[LabeledPoint] = null
   
   protected var frameWork: SparkSession = null
 
   
   def initCSVDataPoint(filePath:String, labelCol:Int = 0) = {
      dataValues = frameWork.read.option("header","true").option("inferSchema", "true").csv(filePath)
      
       dataValuesRDD = dataValues.rdd.map(
           
         row => {
           val rowValues = row.toSeq.toArray.map({
               case s:String =>
                 if (("[".equals(s)) || ("]".equals(s)))  
                   0.0
                 else s.toDouble
           })
           val featureVector = new DenseVector(rowValues.init)
           println(featureVector.toString)
           val label = rowValues(labelCol)
           LabeledPoint(label, featureVector)
         })
  }
   def initCSVDataPoint(filePath:String, dao_Obj:Any, labelCol:Int) = {
      //final case class Titanic (id:Int)
     
      dataValues = frameWork.read.option("header","true").option("inferSchema", "true").csv(filePath)
      
      dataValues.foreach { x => println(x) }
      
       dataValuesRDD = dataValues.rdd.map(
           
         row => {
           val rowValues = row.asInstanceOf[dao_Obj]
           val featureVector = new DenseVector(rowValues.id)
           println(featureVector.toString)
           val label = rowValues(labelCol)
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
   
}