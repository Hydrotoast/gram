package com.giocc.tensor

import java.util

/**
  * A monotonic function from an N-dimensional coordinate system to an M-dimensional coordinate system where N <= M.
  * The size of the domain is always defined to be [0, size) for each dimension. This data structure is immutable.
  *
  * @param _coordinateMaps The ordinal ranges over each dimension. Must be non-empty.
  */
private[tensor] class SubscriptMap(
  private val _coordinateMaps: Array[CoordinateMap]
) {
  require(_coordinateMaps.nonEmpty)

  /**
    * The number of dimensions of the domain. Computed in O(M) time.
    */
  def domainRank: Int = {
    var result = 0
    _coordinateMaps.foreach { coordinateMap =>
      result += (if (coordinateMap.isConstant) 0 else 1)
    }
    result
  }

  /**
    * The number of dimensions of the range.
    */
  def rangeRank: Int = _coordinateMaps.length

  /**
    * The shape of the domain of this map.
    */
  def domainShape: Shape = {
    val domainOrder = domainRank
    val rangeOrder = rangeRank

    val data = new Array[Int](domainOrder)
    var i = 0
    var j = 0
    while (i < rangeOrder) {
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
    * Given a subscript, maps the coordinates to a new coordinate system.
    *
    * @param subscript The subscript to map.
    * @return a new subscript.
    */
  def map(subscript: Subscript): Subscript = {
    Subscript.fromIterator(rangeRank, new SubscriptMapCoordinateIterator(subscript))
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
    * Iterates over the subscript mapping of a coordinate iterator.
    *
    * @param _iterator The coordinate iterator to map over.
    */
  private class SubscriptMapCoordinateIterator(
    _iterator: Subscript
  ) extends Iterator[Int] {
    private var _currentDimension: Int = -1

    override def hasNext: Boolean = {
      _currentDimension < rangeRank - 1
    }

    override def next(): Int = {
      if (!hasNext) {
        throw new IllegalStateException()
      }

      _currentDimension += 1
      val coordinateMap = _coordinateMaps(_currentDimension)
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

private[tensor] object SubscriptMap {

  /**
    * Given coordinate maps, constructs a map over subscripts.
    * @param coordinateMaps A list of maps over each coordinate.
    * @return a map over subscripts.
    */
  def of(coordinateMaps: CoordinateMap*): SubscriptMap = {
    new SubscriptMap(coordinateMaps.toArray)
  }
}
