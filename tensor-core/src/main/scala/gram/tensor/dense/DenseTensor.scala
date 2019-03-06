package gram.tensor.dense

import gram.tensor._
import gram.tensor.linearindex.LinearIndex
import gram.tensor.subscript.Subscript

import scala.{specialized => sp}

/** A dense tensor of a given shape over an element type.
  *
  * @param _shape The shape of the tensor.
  * @param _data  The underlying data storage of the tensor.
  * @tparam A The type of each element in the tensor.
  */
private[tensor] class DenseTensor[@sp A](_shape: Shape, _data: Array[A])
    extends Tensor[A] {
  def shape: Shape =
    _shape

  def apply(ind: Int): A =
    _data(ind)

  def update(ind: Int, value: A): Unit =
    _data(ind) = value

  def apply(sub: Subscript): A = {
    val index = LinearIndex.fromSubscript(sub, _shape)
    _data(index)
  }

  def update(sub: Subscript, value: A): Unit = {
    val index = LinearIndex.fromSubscript(sub, _shape)
    _data(index) = value
  }

  def iterator: Iterator[A] =
    _data.iterator

  def indexStyle: IndexStyle =
    LinearIndexing
}
