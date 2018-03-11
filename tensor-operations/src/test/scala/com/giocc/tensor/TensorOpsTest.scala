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
