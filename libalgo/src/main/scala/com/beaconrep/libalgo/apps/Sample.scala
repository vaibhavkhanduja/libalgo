package com.beaconrep.libalgo.apps

 import org.apache.spark.sql.SparkSession
  
class Sample extends App {
  
case class Person(name: String, age: Long)

val frameWork = SparkSession
  .builder()
  .appName("Spark SQL Example")
  .config("spark.some.config.option", "some-value")
  .getOrCreate()

// For implicit conversions like converting RDDs to DataFrames
import frameWork.implicits._

def myfunction {
    val dataValues = frameWork.read.csv("/tmp").as[Person]
}

}