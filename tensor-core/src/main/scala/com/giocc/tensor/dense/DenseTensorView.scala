package com.giocc.tensor.dense

import com.giocc.tensor._

import scala.{specialized => sp}

/**
  * Represents a view of a tensor e.g. a vector slice of a matrix.
  *
  * @param _base  The base tensor to index from.
  * @param _subscriptMap The cartesian range used to generate the view.
  * @tparam A The type of the elements stored in the tensor.
  */
private[tensor] class DenseTensorView[@sp A](
  _base: Tensor[A],
  _subscriptMap: SubscriptMap
) extends Tensor[A] {
  private val _shape = _subscriptMap.domainShape

  override def shape: Shape = {
    _shape
  }

  override def apply(ind: Int): A = {
    val coordinateIterator = _shape.coordinateIteratorOf(ind)
    val subscriptMappedIterator = _subscriptMap.map(coordinateIterator)
    val index = subscriptMappedIterator.toIndex(_base.shape)
    _base(index)
  }

  override def update(ind: Int, value: A): Unit = {
    val coordinateIterator = _shape.coordinateIteratorOf(ind)
    val subscriptMappedIterator = _subscriptMap.map(coordinateIterator)
    val index = subscriptMappedIterator.toIndex(_base.shape)
    _base.update(index, value)
  }

  override def apply(sub: Subscript): A = {
    val coordinateIterator = sub.coordinateIterator
    val subscriptMappedIterator = _subscriptMap.map(coordinateIterator)
    val index = subscriptMappedIterator.toIndex(_base.shape)
    _base(index)
  }

  override def update(sub: Subscript, value: A): Unit = {
    val coordinateIterator = sub.coordinateIterator
    val rangedMappedIterator = _subscriptMap.map(coordinateIterator)
    val index = rangedMappedIterator.toIndex(_base.shape)
    _base.update(index, value)
  }

  override def elementIterator: Iterator[A] = {
    _shape.subscriptIterator
      .map(_.coordinateIterator)
      .map(_subscriptMap.map)
      .map(_.toIndex(_base.shape))
      .map(apply)
  }

  override def indexStyle: IndexStyle = {
    SubscriptIndexing
  }
}

object DenseTensorView {
  def of[@sp A](
    tensor: Tensor[A],
    subscriptMap: SubscriptMap
  ): DenseTensorView[A] = {
    new DenseTensorView[A](tensor, subscriptMap)
  }
}
