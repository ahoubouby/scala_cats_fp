package com.ahoubouby

import com.ahoubouby.datascture.Functor

object Main extends App {

  val listFunctor = new Functor[List] {
    override def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
  }

  def map2[A, B, C](fa: Option[A], fb: Option[B])(f: (A, B) => C): Option[C] =
    fa.flatMap(a => fb map (b => f(a, b)))

  val optionMap = new Functor[Option] {
    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa match {
      case Some(value) => Some(f(value))
      case None        => None
    }
  }

  val optionA = Some(3)
  val optionB = Some(33)

  val resultOfMap2 = map2(optionA, optionB) { (a, b) =>
    b - a
  }

  /* import com.ahoubouby.datascture.Monad

  val monadOption: Monad[Option] = new Monad[Option] {
    override def unit[A](a: => A): Option[A] = Some(a)

    override def flatMap[A, B](ma: Option[A])(f: A => Option[B]): Option[B] =
      ma.flatMap(f)
  }

  val monadList: Monad[List] = new Monad[List] {
    override def unit[A](a: => A): List[A] = List(a)
    override def flatMap[A, B](ma: List[A])(f: A => List[B]): List[B] =
      ma.flatMap(f)
  }

  val monadStream: Monad[LazyList] = new Monad[LazyList] {
    override def unit[A](a: => A): LazyList[A] = LazyList(a)

    override def flatMap[A, B](
      ma: LazyList[A]
    )(f: A => LazyList[B]): LazyList[B] =
      ma.flatMap(f)
  }*/

}
