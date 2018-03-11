package gram.tensor.dense

import gram.tensor.{Shape, Tensor}
import spire.math.Numeric

import scala.reflect.ClassTag
import scala.{specialized => sp}

object Tensor {
  def zeros[@sp A: Numeric: ClassTag](shape: Shape): Tensor[A] = {
    val data = Array.fill[A](shape.length)(implicitly[Numeric[A]].zero)
    create(shape, data)
  }

  def ones[@sp A: Numeric: ClassTag](shape: Shape): Tensor[A] = {
    val data = Array.fill[A](shape.length)(implicitly[Numeric[A]].one)
    create(shape, data)
  }

  def create[@sp A](
    shape: Shape,
    data: Array[A]
  ): Tensor[A] = {
    shape match {
      case shape: Shape if shape.rank == 1 =>
        new DenseVector[A](shape(0), data)
      case shape: Shape if shape.rank == 2 =>
        new DenseMatrix[A](shape(0), shape(1), data)
      case _ =>
        new DenseTensor[A](shape, data)
    }
  }
}
