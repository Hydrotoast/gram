package com.giocc.tensor.dense

import com.giocc.tensor.{Shape, _}
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class TensorTest extends FunSuite {
  test("Tensor can be constructed with zeros") {
    val a = Tensor.zeros[Int](Shape.of(3, 2))
    a.toArray should contain theSameElementsAs Array.fill[Int](3 * 2)(0)
  }

  test("Tensor can be constructed with ones") {
    val a = Tensor.ones[Int](Shape.of(3, 2))
    a.toArray should contain theSameElementsAs Array.fill[Int](3 * 2)(1)
  }

  test("Tensor should construct views") {
    val a = Tensor.zeros[Int](Shape.of(3, 2))
    a(sub(0, 0)) = 0
    a(sub(1, 0)) = 1
    a(sub(2, 0)) = 2
    a(sub(0, 1)) = 0
    a(sub(1, 1)) = 1
    a(sub(2, 1)) = 2

    val b = a.view(range(slice(0, 3), point(1)))
    b.toArray should contain theSameElementsInOrderAs Array(0, 1, 2)
  }

  test("A dense tensor should equal a view of the same order and same contents") {
    val a = Tensor.zeros[Int](Shape.of(3, 2))
    a(sub(0, 0)) = 0
    a(sub(1, 0)) = 1
    a(sub(2, 0)) = 2
    a(sub(0, 1)) = 0
    a(sub(1, 1)) = 1
    a(sub(2, 1)) = 2
    val b = a.view(range(slice(0, 3), point(1)))

    val c = Tensor.create[Int](Shape.of(3), Array(0, 1, 2))

    b shouldBe c
  }

  test("Two dense tensors with the same contents and order should be equal") {
    val a = Tensor.zeros[Int](Shape.of(4, 3))
    a(sub(2, 2)) = 1

    val b = Tensor.zeros[Int](Shape.of(4, 3))
    b(sub(2, 2)) = 1

    a shouldBe b
  }

  test("Two dense tensors with different contents should not be equal") {
    val a = Tensor.zeros[Int](Shape.of(4, 3))

    // b is different by one element
    val b = Tensor.zeros[Int](Shape.of(4, 3))
    b(sub(2, 2)) = 1

    a should not equal b
  }

  test("Two dense tensors with different shapes should not be equal") {
    val a = Tensor.zeros[Int](Shape.of(3, 2))
    val b = Tensor.zeros[Int](Shape.of(4, 3))
    a should not equal b
  }
}
