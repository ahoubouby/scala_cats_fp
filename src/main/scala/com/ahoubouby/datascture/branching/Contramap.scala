package com.ahoubouby.datascture.branching

import cats.Functor

trait Printable[A] {
  self =>
  def format(value: A): String
  def contramap[B](func: B => A): Printable[B] = new Printable[B] {
    override def format(value: B): String = self.format(func(value))
  }

}
case class Box[A](value: A)

object contramap extends App {
  def format[A](value: A)(implicit p: Printable[A]): String = p.format(value)
  implicit val printableString: Printable[String] = (value: String) =>
    "\"" + value + "\""
  implicit val printableBoolean: Printable[Boolean] = (value: Boolean) =>
    if (value) "yes" else "nop"

  implicit def printableBox[A](
    implicit printable: Printable[A]
  ): Printable[Box[A]] =
    printable.contramap[Box[A]](_.value)
  println(format("hello"))
  println(format(true))

  println(format(Box(false)))
  println(format(Box("false")))

}
