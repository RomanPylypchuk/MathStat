package structure

trait Estimator {
 type DistributionType <: Distribution
}

object Estimator{
  case object NormalMean extends Estimator{
    type DistributionType = Distribution.Normal.type
  }
  case object NormalVar extends Estimator{
    type DistributionType = Distribution.Normal.type
  }
  case object BernoulliP extends Estimator{
    type DistributionType = Distribution.Bernoulli.type
  }
}
