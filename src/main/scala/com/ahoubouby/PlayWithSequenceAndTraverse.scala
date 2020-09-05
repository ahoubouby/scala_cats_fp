package com.ahoubouby

object PlayWithSequenceAndTraverse {

  import scala.util.Try

  val list = List("1", "33", "3")
  val listString = List("abdelwahed", "houbouby")

  val parseInt = (ele: String) => Try(ele.toInt).toOption

  def map2[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] =
    for {
      aa <- a
      bb <- b
    } yield f(aa, bb)

  def sequence[A](list: List[Option[A]]): Option[List[A]] = list match {
    case h :: t => h.flatMap(hh => sequence(t).map(hh :: _))
    case Nil    => Some(Nil)

  }

  sequence(list.map(parseInt))
  // traverse list twice list => option => list:option
  def traverse[A, B](list: List[A])(f: A => Option[B]): Option[List[B]] =
    sequence(list.map(f))

  def traverse2[A, B](list: List[A])(f: A => Option[B]): Option[List[B]] =
    list match {
      case Nil    => Some(Nil)
      case h :: t => map2(f(h), traverse2(t)(f))(_ :: _)
    }
  def sequence2[A](list: List[Option[A]]): Option[List[A]] =
    traverse2(list)(x => x)
  traverse(list)(parseInt)
  traverse2(list)(parseInt)
  sequence2(list.map(parseInt))
  //*********************** natural transformation ****************************//

  trait Functor[F[_]] {
    def map[A, B](f: A => B): F[A] => F[B]
  }

  val length: String => Int = s => s.length

  def safeHead[A]: List[A] => Option[A] = {
    case h :: _ => Some(h)
    case Nil    => None
  }

  val listF = new Functor[List] {
    override def map[A, B](f: A => B): List[A] => List[B] = {
      case head :: tail => f(head) :: map(f)(tail)
      case Nil          => Nil
    }
  }

  val optionF = new Functor[Option] {
    override def map[A, B](f: A => B): Option[A] => Option[B] = {
      case Some(v) => Some(f(v))
      case None    => None
    }
  }

  val mapAndThenTransform: List[String] => Option[Int] = safeHead compose listF
    .map(length)
  val transformAndThenMap
    : List[String] => Option[Int] = optionF.map(length) compose safeHead
  assert(transformAndThenMap(listString) == mapAndThenTransform(listString))

  //*********************** natural transformation ****************************//

}
