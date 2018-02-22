package com.giocc.tensor.iterator

import com.giocc.tensor.Shape

/**
  * Iterates over the valid linear indices within the given shape.
  *
  * @param _shape The shape.
  */
class LinearIndexIterator(
  _shape: Shape
) extends Iterator[Int] {
  private val _length: Int = _shape.length
  private var _item: Int = -1

  override def hasNext: Boolean = {
    _item < _length - 1
  }

  override def next(): Int = {
    if (!hasNext) {
      throw new IllegalStateException()
    }

    _item += 1
    _item
  }
}
