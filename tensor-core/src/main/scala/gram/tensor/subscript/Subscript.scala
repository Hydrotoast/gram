package gram.tensor.subscript

import gram.tensor.Shape

trait Subscript extends Iterable[Int] {

  /** The number of coordinates in this subscript. */
  def rank: Int

  /** Returns the coordinate of the given dimension.
    *
    * @param dimension The dimension.
    * @return The coordinate.
    */
  def apply(dimension: Int): Int

  /** Iterates over the coordinates of each dimension. */
  def iterator: CoordinateIterator

  override def equals(o: Any): Boolean =
    o match {
      case that: Subscript => eq(that) || iterator.sameElements(that.iterator)
      case _               => false
    }
}

object Subscript {

  private final class ArraySubscript(_coordinates: Array[Int])
      extends Subscript {

    def rank: Int =
      _coordinates.length

    def apply(dimension: Int): Int =
      _coordinates(dimension)

    def iterator: CoordinateIterator =
      new Iterator

    private final class Iterator extends CoordinateIterator {
      private val _rank = rank
      private var _currentDimension = 0

      def hasNext: Boolean =
        _currentDimension < _rank

      def next(): Int = {
        if (!hasNext) {
          throw new IllegalStateException(
            "Iterator does not have a next element")
        }

        // Retrieve the size of the current dimension
        val size = ArraySubscript.this.apply(_currentDimension)

        // Move to the next dimension
        _currentDimension += 1

        // Return the size of the requested dimension
        size
      }
    }
  }

  private final class LinearIndexSubscript(_index: Int, _shape: Shape)
      extends Subscript {
    def rank: Int =
      _shape.rank

    def apply(dimension: Int): Int = {
      var currentDimension = 0
      var ind = _index
      var coordinate = ind % _shape(currentDimension)
      currentDimension += 1
      while (currentDimension <= dimension) {
        coordinate = ind % _shape(currentDimension)
        ind /= _shape(currentDimension)
        currentDimension += 1
      }
      coordinate
    }

    def iterator: CoordinateIterator =
      new Iterator(_index)

    private final class Iterator(private var _ind: Int)
        extends CoordinateIterator {
      private val _rank = _shape.rank
      private var _currentDimension = -1

      def hasNext: Boolean =
        _currentDimension < _rank - 1

      def next(): Int = {
        if (!hasNext) {
          throw new IllegalStateException()
        }

        _currentDimension += 1
        val coordinate = _ind % _shape(_currentDimension)
        _ind /= _shape(_currentDimension)

        coordinate
      }
    }
  }

  def of(coordinates: Int*): Subscript =
    new ArraySubscript(coordinates.toArray)

  def fromArray(coordinates: Array[Int]): Subscript =
    new ArraySubscript(coordinates)

  def fromLinearIndex(index: Int, shape: Shape): Subscript =
    new LinearIndexSubscript(index, shape)
}
