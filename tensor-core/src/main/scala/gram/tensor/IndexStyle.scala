package gram.tensor

/**
  * Represents the indexing style of a tensor.
  */
private[tensor] sealed trait IndexStyle

/**
  * Optimal indexing performed by a linear index.
  */
private[tensor] object LinearIndexing extends IndexStyle

/**
  * Optimal indexing perform a subscript index.
  */
private[tensor] object SubscriptIndexing extends IndexStyle
