package com.ahoubouby.datascture
import scala.language.higherKinds

import cats.instances.function._ // for functor
import cats.syntax.functor._ // for map

object Functor extends App {

  import cats.Functor
  import cats.instances.option._

  def doMah[F[_]](start: F[Int])(implicit functor: Functor[F]): F[Int] =
    start.map(x => (x + 1) * 2)

  val option: Option[Int] = Some(3)
  println(doMah(option))

}
