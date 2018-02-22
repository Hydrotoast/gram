package com.giocc.tensor.iterator

import com.giocc.tensor.{Shape, Subscript}

/**
  * Iterates over the valid subscripts within the given shape.
  *
  * @param _shape The shape.
  */
class SubscriptIterator(
  _shape: Shape
) extends Iterator[Subscript] {
  private val _length: Int = _shape.length
  private var _item: Int = 0
  private val _coordinates: Array[Int] = {
    val buffer = new Array[Int](_shape.order)
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
    new Subscript(_coordinates)
  }

  private def updateCoordinates(): Unit = {
    // Find the first position where the coordinate has not reached its upper bound
    var i = 0
    while (i < _coordinates.length && _coordinates(i) == (_shape.apply(i) - 1)) {
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
