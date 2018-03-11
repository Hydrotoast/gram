package com.giocc.tensor.dense

import com.giocc.tensor.{LinearIndexing, LinearIndices, SubscriptIndexing, Subscripts}

import scala.{specialized => sp}

/**
  * Represents binary tensor operations on an underlying tensor.
  *
  * @param underlying The underlying tensor.
  * @tparam A The type of the elements of the tensor.
  */
private[tensor] class TensorOps[@sp A](
  underlying: Tensor[A]
) {

  /**
    * Given a tensor, assigns the elements of the other tensor to the underlying tensor.
    *
    * @param that The other tensor to pull elements from.
    * @return the updated underlying tensor.
    */
  def :=(that: Tensor[A]): Tensor[A] = {
    require(
      underlying.shape.equals(that.shape),
      s"Cannot operate on tensors of different shapes: shape1=${underlying.shape} and shape2=${that.shape}"
    )

    val thatIter = that.iterator
    underlying.indexStyle match {
      case LinearIndexing =>
        val indIter = LinearIndices.fromShape(underlying.shape).iterator
        while (indIter.hasNext && thatIter.hasNext) {
          underlying.update(indIter.next(), thatIter.next())
        }
      case SubscriptIndexing =>
        val indIter = Subscripts.fromShape(underlying.shape).iterator
        while (indIter.hasNext && thatIter.hasNext) {
          underlying.update(indIter.next(), thatIter.next())
        }
    }
    underlying
  }

  /**
    * Given a tensor, adds the elements of the other tensor to the underlying tensor.
    *
    * @param that The other tensor to pull elements from.
    * @return the updated underlying tensor.
    */
  def +=(that: Tensor[A])(implicit num: Numeric[A]): Tensor[A] = {
    require(
      underlying.shape.equals(that.shape),
      s"Cannot operate on tensors of different shapes: shape1=${underlying.shape} and shape2=${that.shape}"
    )

    val thisIter = underlying.iterator
    val thatIter = that.iterator
    underlying.indexStyle match {
      case LinearIndexing =>
        val indIter = LinearIndices.fromShape(underlying.shape).iterator
        while (indIter.hasNext && thatIter.hasNext) {
          underlying.update(indIter.next(), num.plus(thisIter.next(), thatIter.next()))
        }
      case SubscriptIndexing =>
        val indIter = Subscripts.fromShape(underlying.shape).iterator
        while (indIter.hasNext && thatIter.hasNext) {
          underlying.update(indIter.next(), num.plus(thisIter.next(), thatIter.next()))
        }
    }
    underlying
  }

  /**
    * Given a tensor, subtracts the elements of the other tensor from the underlying tensor.
    *
    * @param that The other tensor to pull elements from.
    * @return the updated underlying tensor.
    */
  def -=(that: Tensor[A])(implicit num: Numeric[A]): Tensor[A] = {
    require(
      underlying.shape.equals(that.shape),
      s"Cannot operate on tensors of different shapes: shape1=${underlying.shape} and shape2=${that.shape}"
    )

    val thisIter = underlying.iterator
    val thatIter = that.iterator
    underlying.indexStyle match {
      case LinearIndexing =>
        val indIter = LinearIndices.fromShape(underlying.shape).iterator
        while (indIter.hasNext && thatIter.hasNext) {
          underlying.update(indIter.next(), num.minus(thisIter.next(), thatIter.next()))
        }
      case SubscriptIndexing =>
        val indIter = Subscripts.fromShape(underlying.shape).iterator
        while (indIter.hasNext && thatIter.hasNext) {
          underlying.update(indIter.next(), num.minus(thisIter.next(), thatIter.next()))
        }
    }
    underlying
  }
}

private[tensor] object TensorOps {
  def of[@sp A](underlying: Tensor[A]): TensorOps[A] = {
    new TensorOps[A](underlying)
  }
}
