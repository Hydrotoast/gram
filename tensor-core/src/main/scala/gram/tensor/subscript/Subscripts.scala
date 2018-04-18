package gram.tensor.subscript

import gram.tensor.Shape

trait SubscriptIterator extends Iterator[Subscript]

/**
  * Iterates over subscripts.
  */
private class ShapeSubscriptIterator(_shape: Shape) extends SubscriptIterator {
  private val _length: Int = _shape.length
  private var _cell: Int = 0
  private val _coordinates: Array[Int] = {
    val buffer = new Array[Int](_shape.rank)
    buffer(0) = -1
    buffer
  }
  private val _subscript = Subscript.fromArray(_coordinates)

  override def hasNext: Boolean = {
    _cell < _length
  }

  override def next(): Subscript = {
    if (!hasNext) {
      throw new IllegalStateException()
    }

    updateCoordinates()
    _cell += 1
    _subscript
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

private[tensor] object Subscripts {

  /**
    * Given a shape, constructs the set of subscripts within the shape.
    *
    * @param shape The shape.
    * @return the subscripts.
    */
  def iterateFrom(shape: Shape): SubscriptIterator = {
    new ShapeSubscriptIterator(shape)
  }
}
