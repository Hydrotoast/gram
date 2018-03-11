package com.giocc.tensor

/**
  * The set of linear indices within a given shape.
  * @param _shape The shape.
  */
private[tensor] class LinearIndices(_shape: Shape) extends Iterable[Int] {

  /**
    * The number of dimensions of the shape.
    */
  def rank: Int = _shape.rank

  override def iterator: Iterator[Int] = {
    new LinearIndexIterator()
  }

  /**
    * Iterates over the valid linear indices within the given shape.
    */
  private class LinearIndexIterator() extends Iterator[Int] {
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
}

private[tensor] object LinearIndices {

  /**
    * The set of linear indices within this shape.
    */
  def fromShape(shape: Shape): LinearIndices = {
    new LinearIndices(shape)
  }

}
