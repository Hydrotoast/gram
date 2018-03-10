package com.giocc.tensor

/**
  * A monotonic function over the discrete interval [0, domainSize) to some discrete range.
  */
sealed trait OrdinalRange {

  /**
    * The number of distinct, valid elements in the domain.
    */
  private[tensor] def domainSize: Int

  /**
    * True if this range is a single point.
    */
  def isSingleton: Boolean = domainSize == 1

  /**
    * The point that this function maps to if it is a singleton. The behavior is undefined if this range is not a
    * singleton.
    */
  def point: Int

  /**
    * True if this range is not a single point i.e. has two or more valid points.
    */
  def isIndexable: Boolean = !isSingleton

  /**
    * Given an element of the domain, returns the corresponding mapping. The behavior is undefined if the element
    * outside the domain [0, domainSize).
    *
    * @param x The element of the domain.
    * @return The corresponding mapping.
    */
  def apply(x: Int): Int
}

/**
  * Represents a monotonic function from [0, domainSize) to [0, end).
  *
  * @param end The exclusive end of the range.
  */
final case class ZeroTo(
  end: Int
) extends OrdinalRange {
  require(end > 0, s"end must be positive to produce a valid range: end=$end")

  override def domainSize: Int = end
  override def point: Int = end - 1
  override def apply(x: Int): Int = x
}

/**
  * Represents a monotonic function from [0, domainSize) to [start, end).
  *
  * @param start The inclusive start of the range.
  * @param end   The exclusive end of the range.
  */
final case class Slice(
  start: Int,
  end: Int
) extends OrdinalRange {
  require(end > start, s"end should be greater than start i.e. end > start: $end > $start")

  override def domainSize: Int = end - start
  override def point: Int = start
  override def apply(x: Int): Int = start + x
}

/**
  * Represents a monotonic function from [0, domainSize) to [start, end) with steps in-between.
  *
  * @param start The inclusive start of the range.
  * @param end   The exclusive end of the range.
  * @param step  The step between elements of the range.
  */
final case class Step(
  start: Int,
  end: Int,
  step: Int
) extends OrdinalRange {
  require(end > start, s"end should be greater than start i.e. end > start: $end > $start")
  require(step > 0, s"step should be positive: step=$step")

  override def domainSize: Int = (end - start) / step
  override def point: Int = start
  override def apply(x: Int): Int = start + x * step
}

/**
  * Represents a mapping to a single point. This is always a singleton range.
  *
  * @param point The point to the map to.
  */
final case class Point(
  point: Int
) extends OrdinalRange {
  require(point >= 0, s"point should be non-negative to be valid: point=$point")

  override def domainSize: Int = 1
  override def isSingleton: Boolean = true
  override def apply(x: Int): Int = throw new UnsupportedOperationException
}
