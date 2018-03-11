package com.giocc.tensor

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class ShapeTest extends FunSuite {
  test("Shape should have rank as the same number of dimensions") {
    val shape1 = Shape.of(1)
    val shape2 = Shape.of(1, 2)
    val shape3 = Shape.of(1, 2, 3)

    shape1.rank shouldBe 1
    shape2.rank shouldBe 2
    shape3.rank shouldBe 3
  }

  test("Shape should have its sizes indexable") {
    val shape = Shape.of(4, 5, 6)

    shape(0) shouldBe 4
    shape(1) shouldBe 5
    shape(2) shouldBe 6
  }

  test("Shape should have length as the product of the sizes of each dimension") {
    val shape1 = Shape.of(4)
    val shape2 = Shape.of(4, 5)
    val shape3 = Shape.of(4, 5, 6)

    shape1.length shouldBe 4
    shape2.length shouldBe 4 * 5
    shape3.length shouldBe 4 * 5 * 6
  }

  test("Shape should have at least one dimension") {
    assertThrows[IllegalArgumentException] {
      Shape.of()
    }
  }

  test("Two shapes with the same sizes should be equal to each other") {
    val shape1 = Shape.of(1, 2, 3)
    val shape2 = Shape.of(1, 2, 3)

    shape1 shouldBe shape2
  }

  test("Two shapes with different sizes should not be equal to each other") {
    val shape1 = Shape.of(1, 2, 3)
    val shape2 = Shape.of(4, 5, 6)

    shape1 should not be shape2
  }

  test("Two shapes of different rank should not be equal to each other") {
    val shape1 = Shape.of(1, 2, 3, 4)
    val shape2 = Shape.of(1, 2, 3)

    shape1 should not be shape2
  }
}
