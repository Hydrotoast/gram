package com.giocc.tensor.dense

import com.giocc.tensor.{IndexStyle, Shape, Subscript, SubscriptMap}

import scala.annotation.tailrec
import scala.reflect.ClassTag
import scala.{specialized => sp}

/**
  * Represents an K-order tensor with elements of type A.
  *
  * @tparam A The type of the elements stored in the tensor.
  */
trait Tensor[@sp A] {

  /**
    * The shape of the tensor.
    */
  def shape: Shape

  /**
    * Given an index, retrieves the element at the given index.
    *
    * @param ind The index.
    * @return The element at the given index.
    */
  def apply(ind: Int): A

  /**
    * Given an index and a value, replaces the element at the given index with the given value.
    *
    * @param ind   The index.
    * @param value The value to substitute with.
    */
  def update(ind: Int, value: A): Unit

  /**
    * Given subscript, retrieves the element at the given subscript.
    *
    * @param sub The subscript.
    * @return The element at given subscript.
    */
  def apply(sub: Subscript): A

  /**
    * Given a subscript and a value, replaces the element at the given subscript with the given value.
    *
    * @param subscript The subscript.
    * @param value     The value to substitute with.
    */
  def update(subscript: Subscript, value: A): Unit

  /**
    * Iterates over the elements of the tensor.
    */
  def elementIterator: Iterator[A]

  /**
    * The optimal style of indexing.
    */
  def indexStyle: IndexStyle

  /**
    * Constructs a dense array representation of the tensor.
    */
  def toArray(implicit ev: ClassTag[A]): Array[A] = {
    elementIterator.toArray
  }

  /**
    * Given a susbcript map, constructs a tensor view that maps its subscripts using the subscript map.
    *
    * @param subscriptMap The subscript map.
    * @return The tensor view.
    */
  def view(subscriptMap: SubscriptMap): DenseTensorView[A] = {
    DenseTensorView.of[A](this, subscriptMap)
  }

  override def equals(o: Any): Boolean = {
    o match {
      case that: Tensor[A] =>
        elementIterator.equals()

        @tailrec def areEqual(
          iter1: Iterator[A],
          iter2: Iterator[A]
        ): Boolean = {
          iter1.isEmpty && iter2.isEmpty ||
          (
            iter1.hasNext &&
            iter2.hasNext &&
            iter1.next().equals(iter2.next()) &&
            areEqual(iter1, iter2)
          )
        }

        areEqual(elementIterator, that.elementIterator)
      case _ =>
        false
    }
  }

  override def hashCode(): Int = {
    val iterator = elementIterator
    var h = 1
    while (iterator.hasNext) {
      val elem = iterator.next()
      h = 31 * h + (if (elem == null) 0 else elem.hashCode())
    }
    h
  }
}

object Tensor {
  def zeros[@sp A: Numeric: ClassTag](shape: Shape): Tensor[A] = {
    val data = Array.fill[A](shape.length)(implicitly[Numeric[A]].zero)
    create(shape, data)
  }

  def ones[@sp A: Numeric: ClassTag](shape: Shape): Tensor[A] = {
    val data = Array.fill[A](shape.length)(implicitly[Numeric[A]].one)
    create(shape, data)
  }

  def create[@sp A](
    shape: Shape,
    data: Array[A]
  ): Tensor[A] = {
    shape match {
      case shape: Shape if shape.order == 1 =>
        new DenseVector[A](shape(0), data)
      case shape: Shape if shape.order == 2 =>
        new DenseMatrix[A](shape(0), shape(1), data)
      case _ =>
        new DenseTensor[A](shape, data)
    }
  }
}
