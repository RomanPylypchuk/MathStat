package structure.CIEstimators

import breeze.linalg.sum
import org.apache.commons.math3.distribution.NormalDistribution
import structure.{ConfidenceInterval, Estimator, Param, SampleParams}
import structure.ConfidenceInterval.{NeedEitherSampleOrParams, SampleOrParams}

case class BernoulliSuccessProb(confidence: Double, sampleOrParams: SampleOrParams)
  extends ConfidenceInterval with NeedEitherSampleOrParams {

  type EstimatorType = Estimator.BernoulliP.type

  def interval: (Double, Double) = {
    val sStatistics: SampleParams = sampleOrParams match {
      case Left(sample) => SampleParams.fromSample(sample,
        Map(
          Estimator.BernoulliP -> (sample => Param(sum(sample) / sample.length, "Success probability"))
        )
      )
      case Right(params) => params
    }

    val numSamples = sStatistics.numSamples
    val sampleP = sStatistics.params(Estimator.BernoulliP).value

    val standardNormal = new NormalDistribution()
    val quantile = standardNormal.inverseCumulativeProbability(confidence / 2 + 0.5)
    val delta = quantile * (math.sqrt(sampleP * (1 - sampleP)) / math.sqrt(numSamples))
    (sampleP - delta , sampleP + delta)
  }
}
