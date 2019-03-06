package gram.tensor.dense

import gram.tensor._
import gram.tensor.subscript.Subscript

import scala.{specialized => sp}

/** A dense matrix of a given size over an element type.
  *
  * @param _size The size of the vector.
  * @param _data The underlying data storage of the tensor.
  * @tparam A The type of each element in the tensor.
  */
private[tensor] class DenseVector[@sp A](_size: Int, _data: Array[A])
    extends Tensor[A] {
  def shape: Shape =
    Shape.of(_size)

  def apply(index: Int): A =
    _data(index)

  def update(index: Int, value: A): Unit =
    _data(index) = value

  def apply(subscript: Subscript): A = {
    require(subscript.rank == 1)
    val index = subscript(0)
    _data(index)
  }

  def update(subscript: Subscript, value: A): Unit = {
    require(subscript.rank == 1)
    val index = subscript(0)
    _data(index) = value
  }

  def iterator: Iterator[A] =
    _data.iterator

  def indexStyle: IndexStyle =
    LinearIndexing
}
