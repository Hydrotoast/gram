package com.giocc.tensor.dense

import com.giocc.tensor.{Shape, Subscript}

import scala.{specialized => sp}

/**
  * Represents a dense matrix of a given size over an element type.
  *
  * @param _size The size of the vector.
  * @param _data The underlying data storage of the tensor.
  * @tparam A The type of each element in the tensor.
  */
class DenseVector[@sp A](
  _size: Int,
  _data: Array[A]
) extends Tensor[A] {
  override def shape: Shape = {
    Shape.of(_size)
  }

  override def apply(ind: Int): A = {
    _data(ind)
  }

  override def update(ind: Int, value: A): Unit = {
    _data(ind) = value
  }

  override def apply(sub: Subscript): A = {
    require(sub.order == 1)
    _data(sub(0))
  }

  override def update(sub: Subscript, value: A): Unit = {
    require(sub.order == 1)
    _data(sub(0)) = value
  }

  override def elementIterator: Iterator[A] = {
    _data.iterator
  }
}
