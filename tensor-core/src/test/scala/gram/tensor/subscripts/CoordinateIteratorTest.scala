package gram.tensor.subscripts

import gram.tensor.subscript.CoordinateIterator
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class CoordinateIteratorTest extends FunSuite {
  test("Subscripts should be convertible to an array") {
    val s = CoordinateIterator.of(4, 5, 6)
    (s.toArray should contain).theSameElementsInOrderAs(Array(4, 5, 6))
  }

  test("Subscripts should have a coordinate iterator") {
    val s = CoordinateIterator.of(4, 5, 6)

    val iterator = s
    iterator.hasNext shouldBe true
    iterator.next() shouldBe 4
    iterator.next() shouldBe 5
    iterator.next() shouldBe 6
    iterator.hasNext shouldBe false
  }

  test("Subscripts should have at least one dimension") {
    assertThrows[IllegalArgumentException] {
      CoordinateIterator.of()
    }
  }

  test("Two subscripts with the same coordinates should be equal to each other") {
    val s1 = CoordinateIterator.of(1, 2, 3)
    val s2 = CoordinateIterator.of(1, 2, 3)

    s1 shouldBe s2
  }

  test("Two subscripts with different coordinates should not be equal to each other") {
    val s1 = CoordinateIterator.of(1, 2, 3)
    val s2 = CoordinateIterator.of(4, 5, 6)

    s1 should not be s2
  }

  test("Two subscripts of different rank should not be equal to each other") {
    val s1 = CoordinateIterator.of(1, 2, 3, 4)
    val s2 = CoordinateIterator.of(1, 2, 3)

    s1 should not be s2
  }
}
