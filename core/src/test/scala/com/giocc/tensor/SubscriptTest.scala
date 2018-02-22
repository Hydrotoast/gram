package com.giocc.tensor

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class SubscriptTest extends FunSuite {
  test("Subscripts should have order as the same number of coordinates") {
    val s1 = Subscript.of(1)
    val s2 = Subscript.of(1, 2)
    val s3 = Subscript.of(1, 2, 3)

    s1.order shouldBe 1
    s2.order shouldBe 2
    s3.order shouldBe 3
  }

  test("Subscripts should have their coordinates indexable") {
    val s = Subscript.of(4, 5, 6)

    s(0) shouldBe 4
    s(1) shouldBe 5
    s(2) shouldBe 6
  }

  test("Subscripts should be convertible to an array") {
    val s = Subscript.of(4, 5, 6)
    s.toArray should contain theSameElementsInOrderAs Array(4, 5, 6)
  }

  test("Subscripts should have a coordinate iterator") {
    val s = Subscript.of(4, 5, 6)

    val iterator = s.coordinateIterator
    iterator.hasNext shouldBe true
    iterator.next() shouldBe 4
    iterator.next() shouldBe 5
    iterator.next() shouldBe 6
    iterator.hasNext shouldBe false
  }

  test("Subscripts should have at least one dimension") {
    assertThrows[IllegalArgumentException] {
      Subscript.of()
    }
  }

  test("Two subscripts with the same coordinates should be equal to each other") {
    val s1 = Subscript.of(1, 2, 3)
    val s2 = Subscript.of(1, 2, 3)

    s1 shouldBe s2
  }

  test("Two subscripts with different coordinates should not be equal to each other") {
    val s1 = Subscript.of(1, 2, 3)
    val s2 = Subscript.of(4, 5, 6)

    s1 should not be s2
  }

  test("Two subscripts of different order should not be equal to each other") {
    val s1 = Subscript.of(1, 2, 3, 4)
    val s2 = Subscript.of(1, 2, 3)

    s1 should not be s2
  }
}
