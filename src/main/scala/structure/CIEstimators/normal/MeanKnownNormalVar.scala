package structure.CIEstimators.normal

import breeze.linalg.DenseVector
import breeze.stats.mean
import org.apache.commons.math3.distribution.ChiSquaredDistribution
import structure.{ConfidenceInterval, Estimator, Param, SampleParams}
import structure.ConfidenceInterval.{NeedBothSampleAndParams, SampleOrParams}

case class MeanKnownNormalVar(confidence: Double, sample: DenseVector[Double], params: Map[Estimator, Param])
  extends ConfidenceInterval with NeedBothSampleAndParams{

  type EstimatorType = Estimator.NormalVar.type

  def interval: (Double, Double) = {

    val sStatistics: SampleParams = SampleParams.fromSampleAndParams(
      sample, params,
      (sample, oldparams) => {
        val rvMean = oldparams(Estimator.NormalMean)
        Map(Estimator.NormalVar ->
          Param(mean(sample.map(x => math.pow(x - rvMean, 2))), "Known mean of normal r.v.")
        )
      }
    )
    val numSamples = sStatistics.numSamples
    val sampleVar = sStatistics.params(Estimator.NormalVar).value

    val chiSquared = new ChiSquaredDistribution(numSamples)
    val Array(lQuantile, rQuantile) = Array(confidence / 2 + 0.5, (1 - confidence) / 2).map(chiSquared.inverseCumulativeProbability)
    (numSamples * sampleVar / lQuantile, numSamples * sampleVar / rQuantile)
  }
}
