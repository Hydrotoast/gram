package gram.tensor

import gram.tensor.subscript.Subscript
import org.scalatest.{FunSuite, Matchers}

class SubscriptTest extends FunSuite with Matchers {

  test("Subscript should be constructible from a linear index") {
    val shape = Shape.of(4, 5, 6)

    val coordinateIterator = Subscript
      .fromLinearIndex(6, shape)
      .iterator
    coordinateIterator.hasNext shouldBe true
    coordinateIterator.next() shouldBe 2
    coordinateIterator.next() shouldBe 1
    coordinateIterator.next() shouldBe 0
    coordinateIterator.hasNext shouldBe false
  }
}
