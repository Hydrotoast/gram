package com.giocc.tensor

import java.util

/**
  * A monotonic function from an N-dimensional coordinate system to an M-dimensional coordinate system where N <= M.
  * The size of the domain is always defined to be [0, size) for each dimension. This data structure is immutable.
  *
  * @param _coordinateMaps The ordinal ranges over each dimension. Must be non-empty.
  */
class SubscriptMap(
  private val _coordinateMaps: Array[CoordinateMap]
) {
  self =>
  require(_coordinateMaps.nonEmpty)

  /**
    * The shape of the domain. Computed in O(M) time.
    */
  def domainShape: Shape = {
    val data = new Array[Int](domainOrder)
    var i = 0
    var j = 0
    while (i < _coordinateMaps.length) {
      val coordinateMap = _coordinateMaps(i)
      if (!coordinateMap.isConstant) {
        data(j) = coordinateMap.domainSize
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
    new SubscriptMapCoordinateIterator(coordinateIterator)
  }

  override def equals(other: Any): Boolean = {
    other match {
      case that: SubscriptMap => _coordinateMaps.sameElements(that._coordinateMaps)
      case _ => false
    }
  }

  override def hashCode(): Int = {
    util.Arrays.hashCode(_coordinateMaps.asInstanceOf[Array[Object]])
  }

  /**
    * Given a dimension, return the corresponding ordinal range.
    *
    * @param dimension The dimension.
    * @return The corresponding ordinal range.
    */
  private[tensor] def apply(dimension: Int): CoordinateMap = _coordinateMaps(dimension)

  /**
    * The number of dimensions of the domain. Computed in O(M) time.
    */
  private[tensor] def domainOrder: Int = {
    var result = 0
    var i = 0
    while (i < _coordinateMaps.length) {
      result += (if (_coordinateMaps(i).isConstant) 0 else 1)
      i += 1
    }
    result
  }

  /**
    * The number of dimensions of the range.
    */
  private[tensor] def rangeOrder: Int = _coordinateMaps.length

  /**
    * Iterates over the cartesian range mapping of a coordinate iterator.
    *
    * @param _iterator The coordinate iterator to map over.
    */
  private class SubscriptMapCoordinateIterator(
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
      val coordinateMap = self.apply(_currentDimension)
      if (coordinateMap.isConstant) {
        coordinateMap.singleton
      } else if (_iterator.hasNext) {
        val coordinate = _iterator.next()
        coordinateMap.map(coordinate)
      } else {
        throw new IllegalStateException()
      }
    }
  }
}

object SubscriptMap {
  def of(coordinateMaps: CoordinateMap*): SubscriptMap = {
    new SubscriptMap(coordinateMaps.toArray)
  }
}
