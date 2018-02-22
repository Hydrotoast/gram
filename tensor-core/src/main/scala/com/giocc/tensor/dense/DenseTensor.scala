package com.giocc.tensor.dense

import com.giocc.tensor._

import scala.{specialized => sp}

/**
  * Represents a dense tensor of a given shape over an element type.
  *
  * @param _shape The shape of the tensor.
  * @param _data  The underlying data storage of the tensor.
  * @tparam A The type of each element in the tensor.
  */
class DenseTensor[@sp A](
  _shape: Shape,
  _data: Array[A]
) extends Tensor[A] {
  override def shape: Shape = {
    _shape
  }

  override def apply(ind: Int): A = {
    _data(ind)
  }

  override def update(ind: Int, value: A): Unit = {
    _data(ind) = value
  }

  override def apply(sub: Subscript): A = {
    val subIterator = sub.coordinateIterator
    val index = subIterator.toIndex(_shape)
    _data(index)
  }

  override def update(sub: Subscript, value: A): Unit = {
    val subIterator = sub.coordinateIterator
    val index = subIterator.toIndex(_shape)
    _data(index) = value
  }

  override def elementIterator: Iterator[A] = {
    _data.iterator
  }

  override def indexStyle: IndexStyle = {
    LinearIndexing
  }
}



