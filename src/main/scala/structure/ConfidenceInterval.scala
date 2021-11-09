package structure

import breeze.linalg.DenseVector
import Estimator._
import breeze.stats.mean
import org.apache.commons.math3.distribution.TDistribution

trait ConfidenceInterval {
 type EstimatorType <: Estimator
 val confidence: Double //Perhaps Confidence class?

 def interval: (Double, Double)
}

object ConfidenceInterval {

  type SampleOrParams = Either[DenseVector[Double], SampleParams]

  trait NeedEitherSampleOrParams{
    val sampleOrParams: SampleOrParams
  }

  trait NeedBothSampleAndParams{
    val sample: DenseVector[Double]
    val params: Map[Estimator, Param]
  }

}