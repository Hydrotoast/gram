package gram.tensor

import java.util

import gram.tensor.subscript.{CoordinateIterator, Subscript}

/** A monotonic function from an N-dimensional coordinate system to an
  * M-dimensional coordinate system where N <= M. The size of the domain is
  * always defined to be [0, size) for each dimension. This data structure is
  * immutable.
  *
  * @param _coordinateMaps The ordinal ranges over each dimension. Must be
  *                        non-empty.
  */
private[tensor] class SubscriptMap(
    private val _coordinateMaps: Array[CoordinateMap]) {
  require(_coordinateMaps.nonEmpty)

  /** The number of dimensions of the domain. Computed in O(M) time. */
  def domainRank: Int = {
    var result = 0
    _coordinateMaps.foreach { coordinateMap =>
      result += (if (coordinateMap.isConstant) 0 else 1)
    }
    result
  }

  /** The number of dimensions of the range. */
  def rangeRank: Int = _coordinateMaps.length

  /** The shape of the domain of this map. */
  def domainShape: Shape = {
    val _domainRank = domainRank
    val _rangeRank = rangeRank

    val data = new Array[Int](_domainRank)
    var i = 0
    var j = 0
    while (i < _rangeRank) {
      val coordinateMap = _coordinateMaps(i)
      if (!coordinateMap.isConstant) {
        data(j) = coordinateMap.domainSize
        j += 1
      }
      i += 1
    }
    Shape.fromArray(data)
  }

  def map(input: CoordinateIterator): CoordinateIterator =
    new Iterator(input)

  override def equals(other: Any): Boolean =
    other match {
      case that: SubscriptMap =>
        _coordinateMaps.sameElements(that._coordinateMaps)
      case _ => false
    }

  override def hashCode(): Int =
    util.Arrays.hashCode(_coordinateMaps.asInstanceOf[Array[Object]])

  private final class Iterator(_iterator: CoordinateIterator)
      extends CoordinateIterator {
    private var _currentDimension: Int = -1

    def hasNext: Boolean =
      _currentDimension < rangeRank - 1

    def next(): Int = {
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

  /** Given coordinate maps, constructs a map over subscripts.
    *
    * @param coordinateMaps A list of maps over each coordinate.
    * @return a map over subscripts.
    */
  def of(coordinateMaps: CoordinateMap*): SubscriptMap =
    new SubscriptMap(coordinateMaps.toArray)
}
