package com.giocc.tensor

import com.giocc.tensor.dense.Tensor
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class TensorOpsTest extends FunSuite {
  implicit def mkTensorOps[A](tensor: Tensor[A]): TensorOps[A] = {
    new TensorOps[A](tensor)
  }

  test("TensorOps should assign") {
    val a = Tensor.zeros[Int](Shape.of(3, 2))
    val b = Tensor.ones[Int](Shape.of(3, 2))

    a := b

    val expected = Tensor.ones[Int](Shape.of(3, 2))
    a shouldBe expected
  }

  test("TensorOps should assign with views on rhs") {
    val a = Tensor.zeros[Int](Shape.of(3, 2))
    val b = Tensor.ones[Int](Shape.of(3, 2, 2))

    a := b.view(range(zeroTo(3), zeroTo(2), point(0)))

    val expected = Tensor.ones[Int](Shape.of(3, 2))
    a shouldBe expected
  }

  test("TensorOps should assign with views on the lhs") {
    val a = Tensor.zeros[Int](Shape.of(3, 2, 2))
    val b = Tensor.ones[Int](Shape.of(3, 2))

    a.view(range(zeroTo(3), zeroTo(2), point(0))) := b

    val onesPart = Array.fill[Int](3 * 2)(1)
    val data = new Array[Int](3 * 2 * 2)
    onesPart.copyToArray(data)
    val expected = Tensor.create[Int](Shape.of(3, 2, 4), data)
    a shouldBe expected
  }

  test("TensorOps should not assign with tensors of different shape") {
    val a = Tensor.zeros[Int](Shape.of(3, 2))
    val b = Tensor.ones[Int](Shape.of(3, 3))

    assertThrows[IllegalArgumentException] {
      a := b
    }
  }

  test("TensorOps should not assign with tensors of different rank") {
    val a = Tensor.zeros[Int](Shape.of(3, 2))
    val b = Tensor.ones[Int](Shape.of(3, 2, 4))

    assertThrows[IllegalArgumentException] {
      a := b
    }
  }

  test("TensorOps should add") {
    val a = Tensor.zeros[Int](Shape.of(3, 2))
    val b = Tensor.ones[Int](Shape.of(3, 2))

    a += b

    val expected = Tensor.ones[Int](Shape.of(3, 2))
    a shouldBe expected
  }

  test("TensorOps should not add with tensors of different shape") {
    val a = Tensor.zeros[Int](Shape.of(3, 2))
    val b = Tensor.ones[Int](Shape.of(3, 3))

    assertThrows[IllegalArgumentException] {
      a += b
    }
  }

  test("TensorOps should not add with tensors of different rank") {
    val a = Tensor.zeros[Int](Shape.of(3, 2))
    val b = Tensor.ones[Int](Shape.of(3, 2, 4))

    assertThrows[IllegalArgumentException] {
      a += b
    }
  }

  test("TensorOps should subtract") {
    val a = Tensor.ones[Int](Shape.of(3, 2))
    val b = Tensor.zeros[Int](Shape.of(3, 2))

    a -= b

    val expected = Tensor.ones[Int](Shape.of(3, 2))
    a shouldBe expected
  }

  test("TensorOps should not subtract with tensors of different shape") {
    val a = Tensor.ones[Int](Shape.of(3, 2))
    val b = Tensor.zeros[Int](Shape.of(3, 3))

    assertThrows[IllegalArgumentException] {
      a -= b
    }
  }

  test("TensorOps should not subtract with tensors of different rank") {
    val a = Tensor.ones[Int](Shape.of(3, 2))
    val b = Tensor.zeros[Int](Shape.of(3, 2, 4))

    assertThrows[IllegalArgumentException] {
      a -= b
    }
  }
}
