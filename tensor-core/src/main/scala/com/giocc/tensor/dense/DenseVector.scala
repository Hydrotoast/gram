package com.giocc.tensor.dense

import com.giocc.tensor.{IndexStyle, LinearIndexing, Shape, Subscript}

import scala.{specialized => sp}

/**
  * A dense matrix of a given size over an element type.
  *
  * @param _size The size of the vector.
  * @param _data The underlying data storage of the tensor.
  * @tparam A The type of each element in the tensor.
  */
private[tensor] class DenseVector[@sp A](
  _size: Int,
  _data: Array[A]
) extends Tensor[A] {
  override def shape: Shape = {
    Shape.of(_size)
  }

  override def apply(index: Int): A = {
    _data(index)
  }

  override def update(index: Int, value: A): Unit = {
    _data(index) = value
  }

  override def apply(subscript: Subscript): A = {
    require(subscript.rank == 1)
    val index = subscript.next()
    _data(index)
  }

  override def update(subscript: Subscript, value: A): Unit = {
    require(subscript.rank == 1)
    val index = subscript.next()
    _data(index) = value
  }

  override def iterator: Iterator[A] = {
    _data.iterator
  }

  override def indexStyle: IndexStyle = {
    LinearIndexing
  }
}
