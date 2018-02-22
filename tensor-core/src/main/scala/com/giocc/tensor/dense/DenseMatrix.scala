package com.giocc.tensor.dense

import com.giocc.tensor.{IndexStyle, LinearIndexing, Shape, Subscript}

import scala.{specialized => sp}

/**
  * Represents a dense matrix of a given shape over an element type.
  *
  * @param _rowSize The size of the row dimension.
  * @param _colSize The size of the column dimension.
  * @param _data    The underlying data storage of the tensor.
  * @tparam A The type of each element in the tensor.
  */
class DenseMatrix[@sp A](
  _rowSize: Int,
  _colSize: Int,
  _data: Array[A]
) extends Tensor[A] {
  override def shape: Shape = {
    Shape.of(_rowSize, _colSize)
  }

  override def apply(ind: Int): A = {
    _data(ind)
  }

  override def update(ind: Int, value: A): Unit = {
    _data(ind) = value
  }

  override def apply(sub: Subscript): A = {
    require(sub.order == 2)
    _data(sub(0) + _rowSize * sub(1))
  }

  override def update(sub: Subscript, value: A): Unit = {
    require(sub.order == 2)
    _data(sub(0) + _rowSize * sub(1)) = value
  }

  override def elementIterator: Iterator[A] = {
    _data.iterator
  }

  override def indexStyle: IndexStyle = {
    LinearIndexing
  }
}