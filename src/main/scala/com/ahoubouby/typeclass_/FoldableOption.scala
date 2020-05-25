package com.ahoubouby.typeclass_

object FoldableOption extends Foldable[Option] {
  override def foldRight[A, B](as: Option[A])(z: B)(f: (A, B) => B): B = as match {
    case Some(value) => f(value, z)
    case None        => z
  }

  override def foldLeft[A, B](as: Option[A])(z: B)(f: (B, A) => B): B = as match {
    case Some(value) => f(z, value)
    case None        => z
  }

  override def foldMap[A, B](as: Option[A])(f: A => B)(mb: Monoid[B]): B = as match {
    case None        => mb.zero
    case Some(value) => f(value)
  }
}
