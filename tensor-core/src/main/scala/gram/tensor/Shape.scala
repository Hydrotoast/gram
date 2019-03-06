package gram.tensor

trait Shape extends Iterable[Int] {

  /** The number of dimensions. */
  def rank: Int

  /** Returns the size of the given dimension. The size is always positive.
    *
    * @param dimension The dimension. Must be non-negative.
    * @return The size of the given dimension.
    */
  def apply(dimension: Int): Int

  /** The product of the sizes of each dimension. Useful for allocating dense
    * arrays. This operation takes `O(rank)` time.
    */
  def length: Int = {
    var result = 1
    iterator.foreach {
      result *= _
    }
    result
  }

  /** True if the given object is equal to this object. They are equal if they
    * they have the same dimension sizes. If they are the same object reference,
    * then they must have the same dimension sizes.
    *
    * @param o The object to compare to.
    * @return true if the given object is equal to this object.
    */
  override def equals(o: Any): Boolean =
    o match {
      case that: Shape => eq(that) || iterator.sameElements(that.iterator)
      case _           => false
    }

  /** Iterates over the size of each dimension. */
  def iterator: Iterator[Int] =
    new DimensionSizeIterator

  private final class DimensionSizeIterator extends Iterator[Int] {
    private val _rank = rank
    private var _currentDimension = 0

    def hasNext: Boolean =
      _currentDimension < _rank

    def next(): Int = {
      if (!hasNext) {
        throw new IllegalStateException("Iterator does not have a next element")
      }

      // Retrieve the size of the current dimension
      val size = Shape.this.apply(_currentDimension)

      // Move to the next dimension
      _currentDimension += 1

      // Return the size of the requested dimension
      size
    }
  }

}

object Shape {

  class ArrayShape(private val _sizes: Array[Int]) extends Shape {
    require(_sizes.nonEmpty)

    def rank: Int =
      _sizes.length

    def apply(dimension: Int): Int =
      _sizes(dimension)

  }

  /** Constructs a shape object given a list of sizes for each dimension.
    *
    * @param sizes The list of sizes for each dimension. Must have at least one
    *              size.
    * @return The shape object.
    */
  def of(sizes: Int*): Shape = {
    require(sizes.nonEmpty, "Must have at least on size")
    new ArrayShape(sizes.toArray)
  }

  /** Constructs a shape object given an array of sizes for each dimension. The
    * object will own the array such that changes in the array will be reflected
    * in the object.
    *
    * @param sizes The array of sizes for each dimension. Must have at least one
    *              size.
    * @return The shape object.
    */
  def fromArray(sizes: Array[Int]): Shape = {
    require(sizes.nonEmpty, "Must have at least on size")
    new ArrayShape(sizes)
  }
}
