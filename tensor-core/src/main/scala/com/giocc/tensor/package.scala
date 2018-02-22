package com.giocc

import com.giocc.tensor.dense.{Tensor, TensorOps}

import scala.{specialized => sp}

package object tensor {
  /**
    * Given coordinates, creates a subscript.
    *
    * @param coordinates The coordinates of the subscript.
    * @return The subscript.
    */
  def sub(coordinates: Int*): Subscript = Subscript.of(coordinates: _*)

  /**
    * Given ordinal ranges, creates a cartesian range.
    *
    * @param ordinalRanges The ordinal ranges.
    * @return The cartesian range.
    */
  def range(ordinalRanges: OrdinalRange*): CartesianRange = CartesianRange.of(ordinalRanges: _*)

  /**
    * Given an end index, creates an ordinal range [0, end).
    *
    * @param end The end index.
    * @return The ordinal range.
    */
  def zeroTo(end: Int): ZeroTo = ZeroTo(end)

  /**
    * Given a start index and end index, creates an ordinal range [start, end).
    *
    * @param start The start index.
    * @param end   The end index.
    * @return The ordinal range.
    */
  def slice(start: Int, end: Int): Slice = Slice(start, end)

  /**
    * Given a start index, an end index, and a step size, creates an ordinal range [start, end) with steps in-between.
    *
    * @param start The start index.
    * @param end   The end index.
    * @param step  The step size.
    * @return The ordinal range.
    */
  def step(start: Int, end: Int, step: Int): Step = Step(start, end, step)

  /**
    * Given a point, creates a singleton range at the point.
    *
    * @param point The point.
    * @return The singleton range.
    */
  def point(point: Int): Point = Point(point)

  /**
    * Given a tensor, enriches it binary tensor operations.
    *
    * @param tensor The tensor to wrap.
    * @tparam A The elemenent type of the tensor.
    * @return a tensor with binary tensor operations.
    */
  implicit def mkTensorOps[@sp A](tensor: Tensor[A]): TensorOps[A] = TensorOps.of(tensor)
}
