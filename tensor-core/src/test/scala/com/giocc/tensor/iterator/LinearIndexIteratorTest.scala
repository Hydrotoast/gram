package com.giocc.tensor.iterator

import com.giocc.tensor.Shape
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class LinearIndexIteratorTest extends FunSuite {

    test("Linear index should iterator over valid linear indices across multiple dimensions") {
      val shape = Shape.of(3, 2)
      val linearIndexIterator = shape.linearIndexIterator

      // Lienar index iteration should be equivalent to column-major iteration.
      linearIndexIterator.hasNext shouldBe true
      (0 until 2).foreach { j =>
        (0 until 3).foreach { i =>
          val linearIndex = linearIndexIterator.next()
          linearIndex shouldBe i + 3 * j
        }
      }
      linearIndexIterator.hasNext shouldBe false
    }
}
