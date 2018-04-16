package gram.tensor.subscript

trait CoordinateIterator extends Iterator[Int] {
  override def equals(o: Any): Boolean = {
    o match {
      case that: CoordinateIterator => eq(that) || sameElements(that)
      case _ => false
    }
  }
}

object CoordinateIterator {
  private final class CoordinateIteratorWrapper(
    private val _coordinates: Iterator[Int]
  ) extends CoordinateIterator {

    override def hasNext: Boolean = {
      _coordinates.hasNext
    }

    override def next(): Int = {
      _coordinates.next()
    }
  }

  def of(coordinates: Int*): CoordinateIterator = {
    require(coordinates.nonEmpty)
    new CoordinateIteratorWrapper(coordinates.toIterator)
  }
}
