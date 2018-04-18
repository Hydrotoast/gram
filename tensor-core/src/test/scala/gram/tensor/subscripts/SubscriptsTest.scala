package gram.tensor.subscripts

import gram.tensor.Shape
import gram.tensor.subscript.{Subscript, Subscripts}
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class SubscriptsTest extends FunSuite {
  test("Subscripts should have an iterator over subscripts in a shape") {
    val shape = Shape.of(3, 2)
    val subscriptIterator = Subscripts.iterateFrom(shape)

    // Subscript iteration should be equivalent to multi-dimensional iteration.
    subscriptIterator.hasNext shouldBe true
    (0 until 2).foreach { j =>
      (0 until 3).foreach { i =>
        val subscript = subscriptIterator.next()
        subscript shouldEqual Subscript.of(i, j)
      }
    }
    subscriptIterator.hasNext shouldBe false
  }
}
