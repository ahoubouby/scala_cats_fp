package com.ahoubouby.bank

object Main extends App {

  import scala.util.Try

  /*
  import com.ahoubouby.datascture.{Monoid, State}

  import scala.annotation.tailrec
  import scala.util.Try

  type Balance = Double
  type Money = Double
  type AccountNumber = Long

  //current state

  type Balances = Map[AccountNumber, Balance]

  val balances: Balances = ???

  implicit val balanceMonoid: Monoid[Balance] = new Monoid[Balance] {
    override def zero: Balance = 0.0

    override def append(f: Balance, f2: => Balance): Balance = f + f2
  }

  def updateBalances(txns: List[AnyRef]): State[Balances, Unit] = ???
   */

  def sequence[A](a: List[Option[A]]): Option[List[A]] = a match {
    case Nil    => Some(Nil)
    case h :: l => h.flatMap(hh => sequence(l).map(hh :: _))
  }

  def map2[A, C, B](oa: Option[A], ob: Option[B])(f: (A, B) => C): Option[C] =
    for {
      a <- oa
      b <- ob
    } yield f(a, b)

  def sequence1[A](a: List[Option[A]]): Option[List[A]] =
    a.foldRight[Option[List[A]]](Some(Nil))((h, l) => map2(h, l)(_ :: _))

  def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] =
    sequence(a.map(f))

  def traverse2[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] =
    a.foldRight[Option[List[B]]](Some(Nil))((h, t) => map2(f(h), t)(_ :: _))

  def parseToInt(a: List[String]): Option[List[Int]] =
    sequence1(a.map(i => Try(i.toInt).map(_ * 3).toOption))

  val list = List("1", "2", "3", "N")

  println(parseToInt(list))
  println(traverse(list)(l => Some(l)))
  println(traverse2(list)(l => Some(l)))
}
