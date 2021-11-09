import breeze.linalg.DenseVector
import breeze.stats.variance

package object structure {

  implicit class VarianceDDoF(sample: DenseVector[Double]){
    def varddof(degrees: Int = 0): Double =
      ((sample.length.toDouble - 1) / (sample.length.toDouble - degrees)) * variance(sample)
  }
}
