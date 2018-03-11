package com.giocc.tensor

import java.util

/**
  * The size of each dimension of an N-dimensional coordinate system. This data structure is immutable.
  *
  * @param _sizes The size of each dimension. Must be non-empty.
  */
class Shape(
  private val _sizes: Array[Int]
) {
  require(_sizes.nonEmpty)

  /**
    * The number of dimensions.
    */
  def rank: Int = _sizes.length

  /**
    * Given a dimension, return the size of the dimension.
    *
    * @param dimension The dimension.
    * @return The size of the dimension.
    */
  private[tensor] def apply(dimension: Int): Int = _sizes(dimension)

  /**
    * The product of the sizes of each dimension. Useful for allocating dense arrays. This operation is O(N).
    */
  def length: Int = {
    var result = 1
    _sizes.foreach {
      result *= _
    }
    result
  }

  override def equals(o: Any): Boolean = {
    o match {
      case that: Shape => _sizes.sameElements(that._sizes)
      case _ => false
    }
  }

  override def hashCode(): Int = {
    util.Arrays.hashCode(_sizes)
  }
}

object Shape {

  /**
    * Given an array of sizes for each dimension, constructs a shape.
    * @param sizes The size of each dimension.
    * @return the shape.
    */
  def of(sizes: Int*): Shape = {
    new Shape(sizes.toArray)
  }
}
