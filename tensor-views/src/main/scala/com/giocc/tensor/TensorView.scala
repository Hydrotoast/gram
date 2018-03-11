package com.giocc.tensor

import scala.{specialized => sp}

/**
  * A view of a tensor e.g. a vector slice of a matrix.
  *
  * @param _base         The base tensor to index from.
  * @param _subscriptMap The cartesian range used to generate the view.
  * @tparam A The type of the elements stored in the tensor.
  */
private[tensor] class TensorView[@sp A](
  _base: Tensor[A],
  _subscriptMap: SubscriptMap
) extends Tensor[A] {
  private val _shape = _subscriptMap.domainShape

  override def shape: Shape = {
    _shape
  }

  override def apply(index: Int): A = {
    val subscript = Subscript.fromLinearIndex(index, _shape)
    val baseSubscript = _subscriptMap.map(subscript)
    val baseIndex = LinearIndex.fromSubscript(baseSubscript, _base.shape)
    _base(baseIndex)
  }

  override def update(index: Int, value: A): Unit = {
    val subscript = Subscript.fromLinearIndex(index, _shape)
    val baseSubscript = _subscriptMap.map(subscript)
    val baseIndex = LinearIndex.fromSubscript(baseSubscript, _base.shape)
    _base.update(baseIndex, value)
  }

  override def apply(subscript: Subscript): A = {
    val baseSubscript = _subscriptMap.map(subscript)
    val baseIndex = LinearIndex.fromSubscript(baseSubscript, _base.shape)
    _base(baseIndex)
  }

  override def update(subscript: Subscript, value: A): Unit = {
    val baseSubscript = _subscriptMap.map(subscript)
    val baseIndex = LinearIndex.fromSubscript(baseSubscript, _base.shape)
    _base.update(baseIndex, value)
  }

  override def iterator: Iterator[A] = {
    Subscripts
      .fromShape(_shape)
      .iterator
      .map(_subscriptMap.map)
      .map(LinearIndex.fromSubscript(_, _base.shape))
      .map(apply)
  }

  override def indexStyle: IndexStyle = {
    SubscriptIndexing
  }
}

object TensorView {
  def of[@sp A](tensor: Tensor[A], map: SubscriptMap): TensorView[A] = {
    tensor match {
      // Optimizes cases based on the underlying tensor
      case _ => new TensorView[A](tensor, map)
    }
  }
}
