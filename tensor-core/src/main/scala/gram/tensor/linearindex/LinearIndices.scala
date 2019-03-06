package gram.tensor.linearindex

import gram.tensor.Shape

trait LinearIndexIterator extends Iterator[Int]

/** Iterates over the valid linear indices within the given shape. */
private class ShapeLinearIndexIterator(_shape: Shape)
    extends LinearIndexIterator {
  private val _length: Int = _shape.length
  private var _item: Int = -1

  def hasNext: Boolean =
    _item < _length - 1

  def next(): Int = {
    if (!hasNext) {
      throw new IllegalStateException()
    }

    _item += 1
    _item
  }
}

private[tensor] object LinearIndices {

  /** The set of linear indices within this shape. */
  def iterateFrom(shape: Shape): LinearIndexIterator =
    new ShapeLinearIndexIterator(shape)

}
