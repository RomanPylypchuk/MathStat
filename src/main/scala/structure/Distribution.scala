package structure

sealed trait Distribution
object Distribution{
  case object Normal extends Distribution
  case object Bernoulli extends Distribution
  case object Mixture extends Distribution
}
