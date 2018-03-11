package gram.tensor

/**
  * A map from the discrete interval [0, domainSize) to some discrete range.
  */
private[tensor] sealed trait CoordinateMap {

  /**
    * The number of distinct, valid elements in the domain.
    */
  def domainSize: Int

  /**
    * True if the range is a single point i.e. the map is a constant function.
    */
  def isConstant: Boolean = domainSize == 1

  /**
    * The point that this function maps to if it is a singleton. The behavior is undefined if this range is not a
    * singleton.
    */
  def singleton: Int

  /**
    * Given an element of the domain, returns the corresponding mapping. The behavior is undefined if the element
    * outside the domain [0, domainSize).
    *
    * @param x The element of the domain.
    * @return The corresponding mapping.
    */
  def map(x: Int): Int
}

/**
  * A linear function from [0, end) to [0, end).
  *
  * @param end The exclusive end of the range.
  */
private[tensor] final case class ZeroTo(
  end: Int
) extends CoordinateMap {
  require(end > 0, s"end must be positive to produce a valid range: end=$end")

  override def domainSize: Int = end
  override def singleton: Int = end - 1
  override def map(x: Int): Int = x
}

/**
  * An affine function from [0, domainSize) to [start, end).
  *
  * @param start The inclusive start of the range.
  * @param end   The exclusive end of the range.
  */
private[tensor] final case class Slice(
  start: Int,
  end: Int
) extends CoordinateMap {
  require(end > start, s"end should be greater than start i.e. end > start: $end > $start")

  override def domainSize: Int = end - start
  override def singleton: Int = start
  override def map(x: Int): Int = start + x
}

/**
  * An affine function from [0, domainSize) to [start, end) with steps in-between.
  *
  * @param start The inclusive start of the range.
  * @param end   The exclusive end of the range.
  * @param step  The step between elements of the range.
  */
private[tensor] final case class Step(
  start: Int,
  end: Int,
  step: Int
) extends CoordinateMap {
  require(end > start, s"end should be greater than start i.e. end > start: $end > $start")
  require(step > 0, s"step should be positive: step=$step")

  override def domainSize: Int = (end - start) / step
  override def singleton: Int = start
  override def map(x: Int): Int = start + x * step
}

/**
  * A mapping to a single point. This is always a singleton range.
  *
  * @param singleton The point to the map to.
  */
private[tensor] final case class Point(
  singleton: Int
) extends CoordinateMap {
  require(singleton >= 0, s"point should be non-negative to be valid: point=$singleton")

  override def domainSize: Int = 1
  override def isConstant: Boolean = true
  override def map(x: Int): Int = throw new UnsupportedOperationException
}
