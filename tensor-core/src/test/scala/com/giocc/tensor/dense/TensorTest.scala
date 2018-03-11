package com.giocc.tensor.dense

import com.giocc.tensor.{Shape, _}
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class TensorTest extends FunSuite {
  test("Tensor can be constructed with zeros") {
    val a = Tensor.zeros[Int](Shape.of(3, 2))
    (a.toArray should contain).theSameElementsAs(Array.fill[Int](3 * 2)(0))
  }

  test("Tensor can be constructed with ones") {
    val a = Tensor.ones[Int](Shape.of(3, 2))
    (a.toArray should contain).theSameElementsAs(Array.fill[Int](3 * 2)(1))
  }

  test("Two dense tensors with the same contents and rank should be equal") {
    val a = Tensor.zeros[Int](Shape.of(4, 3))
    a(10) = 1

    val b = Tensor.zeros[Int](Shape.of(4, 3))
    b(10) = 1

    a shouldBe b
  }

  test("Two dense tensors with different contents should not be equal") {
    val a = Tensor.zeros[Int](Shape.of(4, 3))

    // b is different by one element
    val b = Tensor.zeros[Int](Shape.of(4, 3))
    b(10) = 1

    (a should not).equal(b)
  }

  test("Two dense tensors with different shapes should not be equal") {
    val a = Tensor.zeros[Int](Shape.of(3, 2))
    val b = Tensor.zeros[Int](Shape.of(4, 3))
    (a should not).equal(b)
  }
}
