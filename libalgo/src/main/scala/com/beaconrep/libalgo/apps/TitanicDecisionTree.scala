package com.beaconrep.libalgo.apps

import com.beaconrep.libalgo.core._
import com.beaconrep.libalgo._

import org.apache.spark.rdd._
import org.apache.spark.sql._
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.DenseVector

import org.apache.spark.sql.types.{StructType, StructField, IntegerType, LongType, StringType, DoubleType}

object TitanicDecsionTree extends App {
  
  val TitanicDao =
   StructType(
     StructField("PassengerID", IntegerType, false) ::
     StructField("Survived", IntegerType, false) ::
     StructField("Pclass", IntegerType, false) ::
     StructField("Name", StringType, false) ::
     StructField("Sex", StringType, false) ::
     StructField("Age", DoubleType, true) ::
     StructField("SibSp", IntegerType, false) ::
     StructField("Parch", IntegerType, false) ::
     StructField("Ticket", StringType, false) ::
     StructField("Fare", DoubleType, false) ::
     StructField("Cabin", StringType, false) ::
     StructField("Embarked", StringType, false) ::
     Nil)
     
  val frameWork = new SparkFramework("TitanicDecisionTree", "local[*]")
  
  val featureQuery = new String("Survived,PassengerID,Age")
  
  val data = new facts(frameWork.getSession())
  
  data.initCSVDataPoint("resources/titanic/train.csv", TitanicDao, featureQuery)
  
  val algorithm1 = new SparkDecisionTree(frameWork.getSession(), data)
 
  println(algorithm1.buildModel.scoreAndLabels);
  
  val algorithm2 = new XGBoostTree(frameWork.getSession(), data)
  
  algorithm2.buildModel 
  
}
