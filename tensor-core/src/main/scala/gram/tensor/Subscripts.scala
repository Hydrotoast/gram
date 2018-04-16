package gram.tensor

/**
  * The set of subscripts within a shape.
  *
  * @param _shape The shape.
  */
private[tensor] class Subscripts(_shape: Shape) extends Iterable[Subscript] {

  /**
    * An iterator over the subscripts.
    */
  def iterator: Iterator[Subscript] = {
    new SubscriptIterator()
  }

  /**
    * Iterates over subscripts.
    */
  private class SubscriptIterator() extends Iterator[Subscript] {
    private val _length: Int = _shape.length
    private var _item: Int = 0
    private val _coordinates: Array[Int] = {
      val buffer = new Array[Int](_shape.rank)
      buffer(0) = -1
      buffer
    }

    override def hasNext: Boolean = {
      _item < _length
    }

    override def next(): Subscript = {
      if (!hasNext) {
        throw new IllegalStateException()
      }

      updateCoordinates()
      _item += 1
      Subscript.fromArray(_coordinates)
    }

    private def updateCoordinates(): Unit = {
      // Find the first position where the coordinate has not reached its upper bound
      var i = 0
      while (i < _coordinates.length && _coordinates(i) == (_shape(i) - 1)) {
        _coordinates(i) = 0
        i += 1
      }
      // Update the position if we have not reached the end of the coordinates
      if (i == _coordinates.length) {
        throw new IllegalStateException()
      } else { // _coordinates(i) != _shape(i)
        _coordinates(i) += 1
      }
    }
  }

}

private[tensor] object Subscripts {

  /**
    * Given a shape, constructs the set of subscripts within the shape.
    *
    * @param shape The shape.
    * @return the subscripts.
    */
  def fromShape(shape: Shape): Subscripts = {
    new Subscripts(shape)
  }
}
