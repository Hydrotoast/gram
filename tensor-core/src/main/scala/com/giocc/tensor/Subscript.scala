package com.giocc.tensor

import java.util

import com.giocc.tensor.iterator.CoordinateIterator

/**
  * Represents an N-dimensional point with n coordinates. We call the number of dimensions, N, the order. This data
  * structure is immutable.
  *
  * @param _coordinates The array of coordinates for each of the the n dimensions. Must be non-empty.
  */
class Subscript(
  private val _coordinates: Array[Int]
) {
  require(_coordinates.nonEmpty)

  /**
    * The number of dimensions of this subscript.
    */
  def order: Int = _coordinates.length

  /**
    * Given a dimension, returns the corresponding coordinate.
    *
    * @param dim The dimension,
    * @return The corresponding coordinate.
    */
  def apply(dim: Int): Int = _coordinates(dim)

  /**
    * Constructs an array where each index represents a dimension and each element corresponds to the coordinate of
    * the given dimension.
    */
  def toArray: Array[Int] = {
    _coordinates.clone()
  }

  /**
    * A coordinate iterator over the coordinates of this subscript.
    */
  def coordinateIterator: CoordinateIterator = {
    new SubscriptCoordinateIterator(this)
  }

  override def toString: String = {
    _coordinates.mkString("(", ",", ")")
  }

  override def equals(other: Any): Boolean = other match {
    case that: Subscript => _coordinates.sameElements(that._coordinates)
    case _ => false
  }

  override def hashCode(): Int = util.Arrays.hashCode(_coordinates)
}

/**
  * Iterates over each coordinate of a subscript.
  *
  * @param _subscript The subscript to iterate over.
  */
private class SubscriptCoordinateIterator(
  _subscript: Subscript
) extends CoordinateIterator {
  private var _currentDimension: Int = -1

  override def hasNext: Boolean = {
    _currentDimension < _subscript.order - 1
  }

  override def next(): Int = {
    if (!hasNext) {
      throw new IllegalStateException()
    }

    _currentDimension += 1
    _subscript(_currentDimension)
  }
}

object Subscript {
  def of(coordinates: Int*): Subscript = {
    new Subscript(coordinates.toArray)
  }
}