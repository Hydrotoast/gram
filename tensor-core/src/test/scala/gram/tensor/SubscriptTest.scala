package gram.tensor

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class SubscriptTest extends FunSuite {
  test("Subscripts should have rank as the same number of coordinates") {
    val s1 = Subscript.of(1)
    val s2 = Subscript.of(1, 2)
    val s3 = Subscript.of(1, 2, 3)

    s1.rank shouldBe 1
    s2.rank shouldBe 2
    s3.rank shouldBe 3
  }

  test("Subscripts should be convertible to an array") {
    val s = Subscript.of(4, 5, 6)
    (s.toArray should contain).theSameElementsInOrderAs(Array(4, 5, 6))
  }

  test("Subscripts should have a coordinate iterator") {
    val s = Subscript.of(4, 5, 6)

    val iterator = s
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

  test("Two subscripts of different rank should not be equal to each other") {
    val s1 = Subscript.of(1, 2, 3, 4)
    val s2 = Subscript.of(1, 2, 3)

    s1 should not be s2
  }

  test("Subscript should be constructible from a linear index") {
    val shape = Shape.of(4, 5, 6)

    val subscriptIterator = Subscript.fromLinearIndex(6, shape)
    subscriptIterator.hasNext shouldBe true
    subscriptIterator.next() shouldBe 2
    subscriptIterator.next() shouldBe 1
    subscriptIterator.next() shouldBe 0
    subscriptIterator.hasNext shouldBe false
  }
}
