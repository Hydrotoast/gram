## Gram

Gram¹ is a generic and efficient library for numerical computations on
arbitrary n-dimensional tensors for Scala.

[![Build Status][build-status-img]][build-status-link] 

### Motivation

Various libraries exist in other programming languages with high-performance
abstractions for handling n-dimensional objects e.g. numpy in Python and Array
in Julia. However, Scala does not yet have a standard library to do so. The
closest library is [Breeze][breeze-link] which only supports vectors and
matrices i.e. tensors up to order two.

Gram is an ambitious attempt to generalize the abstractions for
n-dimensional tensors.

### Concepts

The main concepts we introduce in this library are: **tensors** and **tensor
views**.

#### Tensor

A tensor is any object that has two properties: a **shape** and a **type**. The
shape is a representation of the sizes of each of the dimensions of a tensor.
The type is the type of each element of the tensor.

For example, consider the constructing a zero matrix with 3 rows and 2 columns
with integer elements.

```scala
// Create a shape with 3 rows and 2 columns
val shape = Shape.of(3, 2)

// Create a matrix with the given shape
val matrix = Tensor.zeros[Int](shape)
```

##### Shape

Recall that a shape is a representation of the sizes of each dimension of a
tensor. We use the factory methods of the `Shape` to construct instances of it.

##### Type

The type of a tensor is the type parameter `A` of any tensor data structure.
Note that with Scala generics, we are able to construct tensors of arbitrary
user-defined types which is behavior that does not exist with numpy.

#### Tensor View

A tensor view is a tensor constructed from another tensor through some
remapping of its indices. For example, an arbitrary vector slice of a matrix is
a tensor view.

```scala
// Create a matrix with 3 rows and 2 columns
val matrix = Tensor.zeros[Int](Shape.of(3, 2))

// Create a vector slice of the second column of the matrix
val vector = matrix.view(range(zeroTo(3), point(1))
```

We create tensor views through by applying ranges to tensors.

##### Range

A range is a selector of some subset of elements in a tensor. For example,
suppose that we have a 3x2 matrix and that we wanted a tensor view that
represented the the second column of the matrix. The range of indices on the
first dimension would be `{0, 1, 2}` and the range of indices on the second
dimension would be `{1}`.

```scala
val rowRange = zeroTo(3)
val colRange = point(1)
```

We may construct ranges over multiple dimensions using **cartesian ranges**. A
cartesian range is a set of single-dimensional ranges. Cartesian ranges select
all cartesian coordinates defined by the cartesian product of its
single-dimensional ranges. So, the cartesian product of `{1, 2, 3}` and `{1}`
would be `{(1, 1), (2, 1), (3, 1)}` which are exactly the indices of the second
column vector of the matrix.

```scala
val rowRange = zeroTo(3)
val colRange = point(1)
val cartesianRange = range(rowRange, colRange)
```

#### Indexing

Indexing may either be performed using **linear indices** (integers) or
**subscripts**.

#### Linear Indices

A linear index is a single integer that indexes a single element by viewing the
n-dimensional tensor as a single array in column-major order. For example,
suppose we had a 3x2 matrix. The linear index 3 will index the element at the
subscript (0, 2).

```scala
// Create a 3-dimensional tensor
val tensor = Tensor.ones[Int](Size.of(4, 3, 2))

// Create a linear index for the last element of the tensor
val index = 23
tensor(index)
```

Note that using susbcripts are fast for indexing dense tensors.

#### Susbcripts

A subscript is an ordered list of n coordinates to index into an n-dimensional
tensor.

```scala
// Create a 3-dimensional tensor
val tensor = Tensor.ones[Int](Size.of(4, 3, 2))

// Create a susbcript for the last element of the tensor
val s = sub(3, 2, 1)
tensor(s)
```

Note that using subscripts are faster than using linear indices for indexing
into a tensor view.

### Tensor Operations

We design tensor operations against the `Tensor[A]` interface. Tensor
operations are only well-defined for tensors of the same shape. We leverage
this constraint to design optimized tensor operations.

#### Optimal Iterators

There are two strategies for designing tensor operations: using linear index
iterators and using subscript index iterators. Recall that dense tensors are
more efficient when indexed with linear indices and that tensor views are more
efficient when indexed with subscripts. Hence, when designing algorithms, we
should allow the runtime type of the object to determine its optimal mechanism
for iterating over its elements. This abstraction allows us to design efficient
algorithms independent of the underlying storage details.

#### Equality Example

Consider designing an equality operation between two tensors. The
`elementIterator` abstraction makes this operation easy to define without
concerning ourselves with the optimal iteration patterns of the underlying
tensors.

```scala
// Two tensors with different implementations
val a = Tensor.ones[Int](Shape.of(3, 2))
val b = Tensor.ones[Int](Shape.of(3, 2, 2))
  .view(range(zeroTo(3), zeroTo(2), point(1)))

// Define operations on tensors independent of their implementation
def areEqual(a: Tensor[Int], b: Tensor[Int]): Boolean = {
  val aIter = a.elementIterator
  val bIter = b.elementIterator
  while (aIter.hasNext && b.hasNext && a.next().equals(b.next())) {}
  !aIter.hasNext && !b.hasNext
}
```

## Future Work

- Create sparse data structures

### Design Choices

We highlight a few important design choices of this library.

#### Performance Choices

- Tensor objects should be mutable.
- Tensor objects should be indexable by linear indices and subscripts.
- Operations should be implemented in terms of iterators.

### Convention Choices

- Tensors are stored in column-major order.
- Ranges are defined as [inclusive, exclusive).
- Operations are only defined on tensors of the same shape.

### Footnotes

[1]: Gram is named after the mathematician 
[Jørgen Pedersen Gram][gram-wikipedia-link] whom incidentally died after being
struck by a bicycle.


[build-status-img]: https://travis-ci.org/Hydrotoast/gram.svg?branch=master
[build-status-link]: https://travis-ci.org/Hydrotoast/gram
[breeze-link]: https://github.com/scalanlp/breeze
[gram-wikipedia-link]: https://en.wikipedia.org/wiki/J%C3%B8rgen_Pedersen_Gram
