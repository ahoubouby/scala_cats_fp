package com.ahoubouby

import com.ahoubouby.datascture.Functor

object Main extends App {

  val listFunctor = new Functor[List] {
    override def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
  }

  val optionMap = new Functor[Option] {
    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa match {
      case Some(value) => Some(f(value))
      case None        => None
    }
  }

  val list              = List(1, 3, 4, 4)
  val listString        = List("abde", "houboub", "hihi", "dd")
  val option: Option[_] = None
  // F[A] zip F[B] => F[(A,B)]
  val zip = list.zip(listString)
  println(zip)
  // F[A,B] unzip => (F[A],F[B])
  println(zip.unzip)
  println(listFunctor.distribute(zip))
  println(optionMap.map(option)(identity))

}
