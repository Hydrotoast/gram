package com.giocc.tensor

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class SubscriptMapTest extends FunSuite {
  test("CartesianRange should have its ordinal ranges indexable") {
    val range = SubscriptMap.of(ZeroTo(2), Point(3), Slice(4, 6))

    range(0) shouldBe ZeroTo(2)
    range(1) shouldBe Point(3)
    range(2) shouldBe Slice(4, 6)
  }

  test("CartesianRange should have at least one ordinal range") {
    assertThrows[IllegalArgumentException] {
      SubscriptMap.of()
    }
  }

  test("CartesianRange should know the shape of its domain") {
    val range = SubscriptMap.of(ZeroTo(2), Slice(4, 6))
    range.domainShape shouldBe Shape.of(2, 2)
  }

  test("CartesianRage should know the order of its domain") {
    val range = SubscriptMap.of(ZeroTo(2), Slice(4, 6))
    range.domainOrder shouldBe 2
  }

  test("CartesianRange should know the shape of its domain with singleton ranges") {
    val range = SubscriptMap.of(ZeroTo(2), Point(3), Slice(4, 6))

    // Note that singleton ordinal ranges should collapse.
    range.domainShape shouldBe Shape.of(2, 2)
  }

  test("CartesianRage should know the order of its domain with singleton ranges") {
    val range = SubscriptMap.of(ZeroTo(2), Point(3), Slice(4, 6))

    // Note that singleton ordinal ranges should collapse.
    range.domainOrder shouldBe 2
  }

  test("CartesianRange should know the order of its range") {
    val range = SubscriptMap.of(ZeroTo(2), Point(3), Slice(4, 6))
    range.rangeOrder shouldBe 3
  }

  test("CartesianRange should map the range over coordinate iterators") {
    val range = SubscriptMap.of(ZeroTo(2), Point(3), Slice(4, 6))
    val coordinateIterator = Subscript.of(1, 0).coordinateIterator

    val rangeMappedIterator = range.map(coordinateIterator)
    rangeMappedIterator.hasNext shouldBe true
    rangeMappedIterator.next() shouldBe 1
    rangeMappedIterator.next() shouldBe 3
    rangeMappedIterator.next() shouldBe 4
    rangeMappedIterator.hasNext shouldBe false
  }
}
