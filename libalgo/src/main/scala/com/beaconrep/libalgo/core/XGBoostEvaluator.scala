package com.beaconrep.libalgo.core

import ml.dmlc.xgboost4j.java.XGBoostError
import ml.dmlc.xgboost4j.scala.{DMatrix, EvalTrait}


class XGBoostEvaluator extends EvalTrait {

  /**
   * get evaluate metric
   *
   * @return evalMetric
   */
  override def getMetric: String = {
    "libalgo_error"
  }

  /**
   * evaluate with predicts and data
   *
   * @param predicts predictions as array
   * @param dmat     data matrix to evaluate
   * @return result of the metric
   */
  override def eval(predicts: Array[Array[Float]], dmat: DMatrix): Float = {
    var error: Float = 0f
    var labels: Array[Float] = null
    try {
      labels = dmat.getLabel
    } catch {
      case ex: XGBoostError =>
        println(ex)
        return -1f
    }
    val nrow: Int = predicts.length
    for (i <- 0 until nrow) {
      if (labels(i) == 0.0 && predicts(i)(0) > 0.5) {
        error += 1
      } else if (labels(i) == 1.0 && predicts(i)(0) <= 0.5) {
        error += 1
      }
    }
    error / labels.length
  }
  
}