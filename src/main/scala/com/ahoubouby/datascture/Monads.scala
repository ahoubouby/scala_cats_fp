package com.ahoubouby.datascture

trait Monads[F[_]] extends Functor[F] {
  def unit[A](a: => A): F[A]
  def flatMap[A, B](ma: F[A])(f: A => F[B]): F[B]
  def map[A, B](ma: F[A])(f: A => B): F[B] = flatMap(ma)(a => unit(f(a)))
  def map2[A, B, C](ma: F[A], mb: F[B])(f: (A, B) => C): F[C] =
    flatMap(ma)(a => map(mb)(b => f(a, b)))

  def flatten[A](mma: F[F[A]]): F[A] = flatMap(mma)(x => x)

  def replicateM[A](n: Int, ma: F[A]): F[List[A]] =
    LazyList.fill(n)(ma).foldRight(unit(List[A]()))(map2(_, _)(_ :: _))

  def product[A, B](ma: F[A], mb: F[B]): F[(A, B)] = map2(ma, mb)((_, _))
}

object MonadsTest extends App {

  val optionMonads = new Monads[Option] {
    override def unit[A](a: => A): Option[A] = Some(a)

    override def flatMap[A, B](ma: Option[A])(f: A => Option[B]): Option[B] =
      ma.flatMap(f)
  }

  val replications = optionMonads.replicateM(4, Some(5))
  val product = optionMonads.product(Some(3), Some("string"))
  println(product)
}
