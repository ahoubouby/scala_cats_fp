package com.ahoubouby.effects

case class Reader[A, B](f: A => B) {
  def apply(a: A): B = f(a)
  def flatMap[C](f2: B => Reader[A, C]): Reader[A, C] = Reader(a => f2(f(a))(a))

}
