package com.giocc.tensor

/**
  * Represents the indexing style of a tensor.
  */
trait IndexStyle

/**
  * Optimal indexing performed by a linear index.
  */
object LinearIndexing extends IndexStyle

/**
  * Optimal indexing perform a subscript index.
  */
object SubscriptIndexing extends IndexStyle
