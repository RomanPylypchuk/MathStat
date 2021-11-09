package structure.CIEstimators.normal

import org.apache.commons.math3.distribution.ChiSquaredDistribution
import structure.{ConfidenceInterval, Estimator, Param, SampleParams, VarianceDDoF}
import structure.ConfidenceInterval.{NeedEitherSampleOrParams, SampleOrParams}
import structure.Estimator.NormalVar

case class AllUknownNormalVar(confidence: Double, sampleOrParams: SampleOrParams)
  extends ConfidenceInterval with NeedEitherSampleOrParams {

  type EstimatorType = Estimator.NormalVar.type

  def interval: (Double, Double) = {
    val sStatistics: SampleParams = sampleOrParams match {
      case Left(sample) => SampleParams.fromSample(sample,
        Map(
          NormalVar -> (sample => Param(sample.varddof(1), "Unbiased Variance"))
        )
      )
      case Right(params) => params
    }

    val numSamples = sStatistics.numSamples
    val sampleVar = sStatistics.params(Estimator.NormalVar).value

    val chiSquared = new ChiSquaredDistribution(numSamples)
    val Array(lQuantile, rQuantile) = Array(confidence / 2 + 0.5, (1 - confidence) / 2).map(chiSquared.inverseCumulativeProbability)
    (numSamples * sampleVar / lQuantile, numSamples * sampleVar / rQuantile)
  }
}
