package com.giocc.tensor

private[tensor] object LinearIndex {

  /**
    * Given a shape, aggregates the remaining coordinates into a cartesian index within the shape.
    *
    * @param shape The shape.
    * @return The cartesian index.
    */
  def fromSubscript(subscript: Subscript, shape: Shape): Int = {
    var dimension = 0
    var ind = 0
    var stride = 1
    val coordinateIterator = subscript
    while (coordinateIterator.hasNext) {
      ind += stride * coordinateIterator.next()
      stride *= shape.apply(dimension)
      dimension += 1
    }
    ind
  }
}
