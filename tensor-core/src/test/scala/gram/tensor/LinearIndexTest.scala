package gram.tensor

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class LinearIndexTest extends FunSuite {

  test("LinearIndex should construct instances from a subscript and shape") {
    val s = Subscript.of(1, 2, 3)
    val shape = Shape.of(4, 5, 6)

    val index = LinearIndex.fromSubscript(s, shape)
    val expectedIndex = 1 + (2 * 4) + (3 * (4 * 5))
    index shouldBe expectedIndex
  }
}
