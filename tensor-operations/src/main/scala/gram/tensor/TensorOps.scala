package gram.tensor

import gram.tensor.linearindex.LinearIndices
import gram.tensor.subscript.Subscripts
import spire.implicits._
import spire.math.Numeric

import scala.{specialized => sp}

/** Binary tensor operations on an underlying tensor.
  *
  * @tparam A The type of the elements of the tensor.
  */
private[tensor] class TensorOps[@sp A](underlying: Tensor[A]) {

  /** Given a tensor, assigns the elements of the other tensor to the underlying
    * tensor.
    *
    * @param that The other tensor to pull elements from.
    * @return the updated underlying tensor.
    */
  def :=(that: Tensor[A]): Tensor[A] = {
    checkShapes(that.shape)

    val it = that.iterator

    update {
      it.next()
    }

    underlying
  }

  /** Given a tensor, adds the elements of the other tensor to the underlying
    * tensor.
    *
    * @param that The other tensor to pull elements from.
    * @return the updated underlying tensor.
    */
  def +=(that: Tensor[A])(implicit num: Numeric[A]): Tensor[A] = {
    checkShapes(that.shape)

    val it1 = underlying.iterator
    val it2 = that.iterator

    update {
      it1.next() + it2.next()
    }

    underlying
  }

  /** Given a tensor, subtracts the elements of the other tensor from the
    * underlying tensor.
    *
    * @param that The other tensor to pull elements from.
    * @return the updated underlying tensor.
    */
  def -=(that: Tensor[A])(implicit num: Numeric[A]): Tensor[A] = {
    checkShapes(that.shape)

    val it1 = underlying.iterator
    val it2 = that.iterator

    update {
      it1.next() - it2.next()
    }

    underlying
  }

  /** Given a code block, update each element of the underlying tensor by
    * applying the code block.
    *
    * @param block The code block.
    */
  private def update(block: => A): Unit =
    underlying.indexStyle match {
      case LinearIndexing =>
        val indIter = LinearIndices.iterateFrom(underlying.shape)
        while (indIter.hasNext) {
          underlying.update(indIter.next(), block)
        }
      case SubscriptIndexing =>
        val indIter = Subscripts.iterateFrom(underlying.shape)
        while (indIter.hasNext) {
          underlying.update(indIter.next(), block)
        }
    }

  /** Given a shape, checks that the shape matches the underlying shape.
    *
    * @param shape The shape to check.
    */
  private def checkShapes(shape: Shape): Unit =
    require(
      underlying.shape.equals(shape),
      s"Cannot operate on tensors of different shapes: shape1=${underlying.shape} and shape2=$shape"
    )
}
