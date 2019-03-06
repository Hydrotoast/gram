package gram.tensor.dense

import gram.tensor._
import gram.tensor.subscript.Subscript

import scala.{specialized => sp}

/** A dense matrix of a given shape over an element type.
  *
  * @param _rowSize The size of the row dimension.
  * @param _colSize The size of the column dimension.
  * @param _data    The underlying data storage of the tensor.
  * @tparam A The type of each element in the tensor.
  */
private[tensor] class DenseMatrix[@sp A](
    _rowSize: Int,
    _colSize: Int,
    _data: Array[A])
    extends Tensor[A] {
  def shape: Shape =
    Shape.of(_rowSize, _colSize)

  def apply(index: Int): A =
    _data(index)

  def update(index: Int, value: A): Unit =
    _data(index) = value

  def apply(subscript: Subscript): A = {
    require(subscript.rank == 2)
    val column = subscript(0)
    val row = subscript(1)
    _data(column + _rowSize * row)
  }

  def update(subscript: Subscript, value: A): Unit = {
    require(subscript.rank == 2)
    val column = subscript(0)
    val row = subscript(1)
    _data(column + _rowSize * row) = value
  }

  def iterator: Iterator[A] =
    _data.iterator

  def indexStyle: IndexStyle =
    LinearIndexing
}
