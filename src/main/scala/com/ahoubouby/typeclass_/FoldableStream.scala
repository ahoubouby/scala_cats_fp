package com.ahoubouby.typeclass_

object FoldableStream extends Foldable[LazyList] {
  override def foldRight[A, B](as: LazyList[A])(z: B)(f: (A, B) => B): B = as.foldRight(z)(f)

  override def foldLeft[A, B](as: LazyList[A])(z: B)(f: (B, A) => B): B = as.foldLeft(z)(f)

  override def foldMap[A, B](as: LazyList[A])(f: A => B)(mb: Monoid[B]): B =
    foldRight(as)(mb.zero)((a, b) => mb.op(b, f(a)))
}
