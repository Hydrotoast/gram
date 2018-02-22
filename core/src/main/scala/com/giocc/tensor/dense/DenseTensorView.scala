package com.giocc.tensor.dense

import com.giocc.tensor._
import com.giocc.tensor.iterator.SubscriptIterator

import scala.{specialized => sp}

/**
  * Represents a view of a tensor e.g. a vector slice of a matrix.
  *
  * @param _shape The shape of the view.
  * @param _range The cartesian range used to generate the view.
  * @param _base  The base tensor to index from.
  * @tparam A The type of the elements stored in the tensor.
  */
class DenseTensorView[@sp A](
  _shape: Shape,
  _range: CartesianRange,
  _base: Tensor[A]
) extends Tensor[A] {
  override def shape: Shape = {
    _shape
  }

  override def apply(ind: Int): A = {
    val coordinateIterator = _shape.coordinateIteratorOf(ind)
    val rangeMappedIterator = _range.map(coordinateIterator)
    val index = rangeMappedIterator.toIndex(_base.shape)
    _base(index)
  }

  override def update(ind: Int, value: A): Unit = {
    val coordinateIterator = _shape.coordinateIteratorOf(ind)
    val rangeMappedIterator = _range.map(coordinateIterator)
    val index = rangeMappedIterator.toIndex(_base.shape)
    _base.update(index, value)
  }

  override def apply(sub: Subscript): A = {
    val coordinateIterator = sub.coordinateIterator
    val rangeMappedIterator = _range.map(coordinateIterator)
    val index = rangeMappedIterator.toIndex(_base.shape)
    _base(index)
  }

  override def update(sub: Subscript, value: A): Unit = {
    val coordinateIterator = sub.coordinateIterator
    val rangedMappedIterator = _range.map(coordinateIterator)
    val index = rangedMappedIterator.toIndex(_base.shape)
    _base.update(index, value)
  }

  override def elementIterator: Iterator[A] = {
    new DenseTensorViewValueIterator[A](this, _shape.subscriptIterator)
  }
}

/**
  * Iterates over the values of a tensor view using subscript indexing.
  *
  * @param _tensorView        The tensor view.
  * @param _subscriptIterator The subscript iterator to perform indexing on.
  * @tparam A The element type of the tensor.
  */
class DenseTensorViewValueIterator[@sp A](
  _tensorView: DenseTensorView[A],
  _subscriptIterator: SubscriptIterator
) extends Iterator[A] {
  override def hasNext: Boolean = {
    _subscriptIterator.hasNext
  }

  override def next(): A = {
    if (!hasNext) {
      throw new IllegalStateException()
    }

    val subscript = _subscriptIterator.next()
    _tensorView(subscript)
  }
}

object DenseTensorView {
  def of[@sp A](
    shape: Shape,
    range: CartesianRange,
    tensor: Tensor[A]
  ): DenseTensorView[A] = {
    new DenseTensorView[A](shape, range, tensor)
  }
}