package com.giocc.tensor.dense

import com.giocc.tensor.Shape
import org.scalatest.FunSuite
import org.scalatest.Matchers._

import com.giocc.tensor._

class TensorOpsTest extends FunSuite {
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

  test("TensorOps should not assign with tensors of different order") {
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

  test("TensorOps should not add with tensors of different order") {
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

  test("TensorOps should not subtract with tensors of different order") {
    val a = Tensor.ones[Int](Shape.of(3, 2))
    val b = Tensor.zeros[Int](Shape.of(3, 2, 4))

    assertThrows[IllegalArgumentException] {
      a -= b
    }
  }
}
