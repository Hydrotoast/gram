package com.giocc.tensor.iterator

import com.giocc.tensor.{Shape, Subscript}
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class CoordinateIteratorTest extends FunSuite {
  test("CoordinateIterator should materialize indices given a shape") {
    val s = Subscript.of(1, 2, 3)
    val shape = Shape.of(4, 5, 6)
    val coordinateIterator = s.coordinateIterator

    val index = coordinateIterator.toIndex(shape)
    val expectedIndex = 1 + (2 * 4) + (3 * (4 * 5))
    index shouldBe expectedIndex
  }
}
