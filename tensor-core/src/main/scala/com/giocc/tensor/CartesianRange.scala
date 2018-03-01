package com.giocc.tensor

import java.util

import com.giocc.tensor.iterator.CoordinateIterator

/**
  * A monotonic function from an N-dimensional coordinate system to an M-dimensional coordinate system where N <= M.
  * The size of the domain is always defined to be [0, size) for each dimension. This data structure is immutable.
  *
  * @param _ordinalRanges The ordinal ranges over each dimension. Must be non-empty.
  */
class CartesianRange(
  private val _ordinalRanges: Array[OrdinalRange]
) {
  require(_ordinalRanges.nonEmpty)

  /**
    * Given a dimension, return the corresponding ordinal range.
    *
    * @param dimension The dimension.
    * @return The corresponding ordinal range.
    */
  def apply(dimension: Int): OrdinalRange = _ordinalRanges(dimension)

  /**
    * The shape of the domain. Computed in O(M) time.
    */
  def domainShape: Shape = {
    val data = new Array[Int](domainOrder)
    var i = 0
    var j = 0
    while (i < _ordinalRanges.length) {
      val ordinalRange = _ordinalRanges(i)
      if (ordinalRange.isIndexable) {
        data(j) = ordinalRange.domainSize
        j += 1
      }
      i += 1
    }
    new Shape(data)
  }

  /**
    * Given a coordinate iterator, maps the coordinates to a new coordinate system through the cartesian range.
    *
    * @param coordinateIterator The coordinate iterator to map.
    * @return A new coordinate iterator mapped by the cartesian range.
    */
  def map(coordinateIterator: CoordinateIterator): CoordinateIterator = {
    new CartesianRangeCoordinateIterator(coordinateIterator)
  }

  override def equals(other: Any): Boolean = {
    other match {
      case that: CartesianRange => _ordinalRanges.sameElements(that._ordinalRanges)
      case _ => false
    }
  }

  override def hashCode(): Int = {
    util.Arrays.hashCode(_ordinalRanges.asInstanceOf[Array[Object]])
  }

  /**
    * The number of dimensions of the domain. Computed in O(M) time.
    */
  private[tensor] def domainOrder: Int = {
    var result = 0
    var i = 0
    while (i < _ordinalRanges.length) {
      result += (if (_ordinalRanges(i).isIndexable) 1 else 0)
      i += 1
    }
    result
  }

  /**
    * The number of dimensions of the range.
    */
  private[tensor] def rangeOrder: Int = _ordinalRanges.length

  /**
    * Iterates over the cartesian range mapping of a coordinate iterator.
    *
    * @param _iterator The coordinate iterator to map over.
    */
  private class CartesianRangeCoordinateIterator(
    _iterator: CoordinateIterator
  ) extends CoordinateIterator {
    private var _currentDimension: Int = -1

    override def hasNext: Boolean = {
      _currentDimension < rangeOrder - 1
    }

    override def next(): Int = {
      if (!hasNext) {
        throw new IllegalStateException()
      }

      _currentDimension += 1
      val unitRange = apply(_currentDimension)
      if (unitRange.isSingleton) {
        unitRange.point
      } else if (_iterator.hasNext) {
        val coordinate = _iterator.next()
        unitRange(coordinate)
      } else {
        throw new IllegalStateException()
      }
    }
  }

}

object CartesianRange {
  def of(ordinalRanges: OrdinalRange*): CartesianRange = {
    new CartesianRange(ordinalRanges.toArray)
  }
}