package structure

import breeze.linalg.DenseVector

case class SampleParams(params: Map[Estimator, Param], numSamples: Int)

object SampleParams{
  def fromSample(sample: DenseVector[Double],
                 f: Map[Estimator, DenseVector[Double] => Param]): SampleParams =
    SampleParams(f.map{case (e, pf) => e -> pf(sample)}, sample.length)

  def fromSampleAndParams(sample: DenseVector[Double],
                          params: Map[Estimator, Param],
                          newParams: (DenseVector[Double], Map[Estimator, Param]) => Map[Estimator, Param]): SampleParams =
    SampleParams(newParams(sample, params), sample.length)
}
