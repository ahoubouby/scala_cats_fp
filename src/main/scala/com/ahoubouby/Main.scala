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
  val optionB: Option[Int] = None

  val resultOfMap2 = map2(optionA, optionB) { (a, b) =>
    b - a
  }
  println(resultOfMap2)

}
