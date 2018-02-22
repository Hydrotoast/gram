package com.giocc.tensor.iterator

import com.giocc.tensor.{Shape, Subscript}
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class SubscriptIteratorTest extends FunSuite {
  test("SusbcriptIterator should iterator over subscripts across multiple dimensions") {
    val shape = Shape.of(3, 2)
    val subscriptIterator = shape.subscriptIterator

    // Subscript iteration should be equivalent to multi-dimensional iteration.
    subscriptIterator.hasNext shouldBe true
    (0 until 2).foreach { j =>
      (0 until 3).foreach { i =>
        val subscript = subscriptIterator.next()
        subscript shouldBe Subscript.of(i, j)
      }
    }
    subscriptIterator.hasNext shouldBe false
  }
}
