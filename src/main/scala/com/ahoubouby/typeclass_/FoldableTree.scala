package com.ahoubouby.typeclass_

object FoldableTree extends Foldable[Tree] {
  override def foldRight[A, B](as: Tree[A])(z: B)(f: (A, B) => B): B = as match {
    case Leaf(value)  => f(value, z)
    case Branch(l, r) => foldRight(l)(foldRight(r)(z)(f))(f)
  }

  override def foldLeft[A, B](as: Tree[A])(z: B)(f: (B, A) => B): B = as match {
    case Leaf(value)  => f(z, value)
    case Branch(l, r) => foldLeft(r)(foldLeft(l)(z)(f))(f)
  }

  override def foldMap[A, B](as: Tree[A])(f: A => B)(mb: Monoid[B]): B = as match {
    case Leaf(a)      => f(a)
    case Branch(l, r) => mb.op(foldMap(r)(f)(mb), foldMap(l)(f)(mb))
  }
}
