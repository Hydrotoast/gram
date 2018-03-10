package com.giocc.tensor.iterator

import com.giocc.tensor.Shape

/**
  * Iterates over N-dimensional coordinates.
  */
trait CoordinateIterator extends Iterator[Int] {

  /**
    * Given a shape, aggregates the remaining coordinates into a cartesian index within the shape.
    *
    * @param shape The shape.
    * @return The cartesian index.
    */
  def toIndex(shape: Shape): Int = {
    var dimension = 0
    var ind = 0
    var stride = 1
    while (hasNext) {
      val coordinate = next()
      ind += stride * coordinate
      stride *= shape.apply(dimension)
      dimension += 1
    }
    ind
  }
}
