package gram.tensor.linearindex

import gram.tensor.Shape
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class LinearIndicesTest extends FunSuite {

  test("LinearIndices should iterate over valid linear indices in a shape") {
    val shape = Shape.of(3, 2)
    val linearIndexIterator = LinearIndices.iterateFrom(shape)

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
