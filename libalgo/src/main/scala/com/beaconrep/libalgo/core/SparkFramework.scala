package com.beaconrep.libalgo.core

import com.beaconrep.libalgo._

import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf


class SparkFramework(applicationName:String, sparkMaster:String) extends SparkSession.Builder with framework {
 
  super.config(new SparkConf().setAppName(applicationName).setMaster(sparkMaster))
  
  val session = super.getOrCreate()
  
  def getSession() = this.getOrCreate()
   
}