package structure.CIEstimators.normal

import breeze.stats.mean
import org.apache.commons.math3.distribution.TDistribution
import structure.ConfidenceInterval.{NeedEitherSampleOrParams, SampleOrParams}
import structure.Estimator._
import structure._

case class AllUnknownNormalMean(confidence: Double, sampleOrParams: SampleOrParams)
  extends ConfidenceInterval with NeedEitherSampleOrParams {

  type EstimatorType = Estimator.NormalMean.type

  def interval: (Double, Double) = {

    val sStatistics: SampleParams = sampleOrParams match {
      case Left(sample) => SampleParams.fromSample(sample,
        Map(
          NormalMean -> (sample => Param(mean(sample), "Sample Mean")),
          NormalVar -> (sample => Param(sample.varddof(1), "Unbiased Variance"))
        )
      )
      case Right(params) => params
    }

    val sampleVar = sStatistics.params(NormalVar).value
    val sampleMean = sStatistics.params(NormalMean).value

    val student = new TDistribution(sStatistics.numSamples - 1)
    val quantile = student.inverseCumulativeProbability(confidence / 2 + 0.5)
    val delta = math.sqrt(sampleVar) / math.sqrt(sStatistics.numSamples) * quantile
    (sampleMean - delta, sampleMean + delta)

  }
}
