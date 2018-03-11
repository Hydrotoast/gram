package com.giocc.tensor

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class SubscriptMapTest extends FunSuite {
  test("SubscriptMap should have at least one coordinate map") {
    assertThrows[IllegalArgumentException] {
      SubscriptMap.of()
    }
  }

  test("SubscriptMap should know the shape of its domain") {
    val subscriptMap = SubscriptMap.of(ZeroTo(2), Slice(4, 6))
    subscriptMap.domainShape shouldBe Shape.of(2, 2)
  }

  test("SubscriptMap should know the rank of its domain") {
    val subscriptMap = SubscriptMap.of(ZeroTo(2), Slice(4, 6))
    subscriptMap.domainRank shouldBe 2
  }

  test("SubscriptMap should know the shape of its domain with singleton coordinates") {
    val subscriptMap = SubscriptMap.of(ZeroTo(2), Point(3), Slice(4, 6))

    // Note that singleton ordinal subscriptMaps should collapse.
    subscriptMap.domainShape shouldBe Shape.of(2, 2)
  }

  test("CartesianRage should know the rank of its domain with singleton coordinates") {
    val subscriptMap = SubscriptMap.of(ZeroTo(2), Point(3), Slice(4, 6))

    // Note that singleton ordinal subscriptMaps should collapse.
    subscriptMap.domainRank shouldBe 2
  }

  test("SubscriptMap should know the rank of its range") {
    val subscriptMap = SubscriptMap.of(ZeroTo(2), Point(3), Slice(4, 6))
    subscriptMap.rangeRank shouldBe 3
  }

  test("SubscriptMap should map the subscriptMap over coordinate iterators") {
    val subscriptMap = SubscriptMap.of(ZeroTo(2), Point(3), Slice(4, 6))
    val subscript = Subscript.of(1, 0)

    val outputSubscript = subscriptMap.map(subscript)
    outputSubscript.hasNext shouldBe true
    outputSubscript.next() shouldBe 1
    outputSubscript.next() shouldBe 3
    outputSubscript.next() shouldBe 4
    outputSubscript.hasNext shouldBe false
  }
}
