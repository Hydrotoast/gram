package gram.tensor

import gram.tensor.linearindex.LinearIndex
import gram.tensor.subscript.{CoordinateIterator, Subscript, Subscripts}

import scala.{specialized => sp}

/** A view of a tensor e.g. a vector slice of a matrix.
  *
  * @param _base         The base tensor to index from.
  * @param _subscriptMap The cartesian range used to generate the view.
  * @tparam A The type of the elements stored in the tensor.
  */
private[tensor] class TensorView[@sp A](
    _base: Tensor[A],
    _subscriptMap: SubscriptMap)
    extends Tensor[A] {
  private val _shape = _subscriptMap.domainShape

  def shape: Shape =
    _shape

  def apply(index: Int): A = {
    val subscript = Subscript.fromLinearIndex(index, _shape)
    val baseSubscript = _subscriptMap.map(subscript.iterator)
    val baseIndex =
      LinearIndex.fromCoordinateIterator(baseSubscript, _base.shape)
    _base(baseIndex)
  }

  def update(index: Int, value: A): Unit = {
    val subscript = Subscript.fromLinearIndex(index, _shape)
    val baseSubscript = _subscriptMap.map(subscript.iterator)
    val baseIndex =
      LinearIndex.fromCoordinateIterator(baseSubscript, _base.shape)
    _base.update(baseIndex, value)
  }

  def apply(subscript: Subscript): A = {
    val baseSubscript = _subscriptMap.map(subscript.iterator)
    val baseIndex =
      LinearIndex.fromCoordinateIterator(baseSubscript, _base.shape)
    _base(baseIndex)
  }

  def update(subscript: Subscript, value: A): Unit = {
    val baseSubscript = _subscriptMap.map(subscript.iterator)
    val baseIndex =
      LinearIndex.fromCoordinateIterator(baseSubscript, _base.shape)
    _base.update(baseIndex, value)
  }

  def iterator: Iterator[A] =
    Subscripts
      .iterateFrom(_shape)
      .map(_.iterator)
      .map(_subscriptMap.map)
      .map(LinearIndex.fromCoordinateIterator(_, _base.shape))
      .map(apply)

  def indexStyle: IndexStyle =
    SubscriptIndexing
}

object TensorView {
  def of[@sp A](tensor: Tensor[A], map: SubscriptMap): TensorView[A] =
    tensor match {
      // Optimizes cases based on the underlying tensor
      case _ => new TensorView[A](tensor, map)
    }
}
