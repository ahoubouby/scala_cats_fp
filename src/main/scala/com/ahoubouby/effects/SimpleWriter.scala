package com.ahoubouby.effects

trait Monad[F[_]] {
  def pure[A](a: A): F[A]
  def map[A, B](fa: F[A])(f: A => B): F[B]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}

object Monad {
  implicit class Ops[F[_], A](fa: F[A])(implicit m: Monad[F]) {
    def map[B](f: A => B): F[B]        = m.map(fa)(f)
    def flatMap[B](f: A => F[B]): F[B] = m.flatMap(fa)(f)
  }

  implicit val writerMonad: Monad[SimpleWriter] = new Monad[SimpleWriter] {
    override def pure[A](a: A): SimpleWriter[A] = SimpleWriter.pure(a)

    override def map[A, B](fa: SimpleWriter[A])(f: A => B): SimpleWriter[B] = fa.map(f)

    override def flatMap[A, B](fa: SimpleWriter[A])(f: A => SimpleWriter[B]): SimpleWriter[B] = fa.flatMap(f)
  }

  implicit val ioMonad: Monad[IO] = new Monad[IO] {
    override def pure[A](a: A): IO[A] = IO.pure(a)

    override def map[A, B](fa: IO[A])(f: A => B): IO[B] = fa.map(f)

    override def flatMap[A, B](fa: IO[A])(f: A => IO[B]): IO[B] = fa.flatMap(f)
  }
}
case class SimpleWriter[A](log: List[String], value: A) {
  def flatMap[B](f: A => SimpleWriter[B]): SimpleWriter[B] = {
    val wb: SimpleWriter[B] = f(value)
    SimpleWriter(log ++ wb.log, wb.value)
  }

  def map[B](f: A => B): SimpleWriter[B] = SimpleWriter(log, f(value))

}
object SimpleWriter {
  def pure[A](value: A): SimpleWriter[A]       = SimpleWriter(Nil, value)
  def log(message: String): SimpleWriter[Unit] = SimpleWriter(List(message), ())
}

case class IO[A](operation: () => A) {
  def flatMap[B](f: A => IO[B]): IO[B] =
    IO.suspend {
      f(operation()).operation()
    }
  def map[B](f: A => B): IO[B] = IO.suspend(f(operation()))
}

object IO {
  def suspend[A](op: => A): IO[A] = IO(() => op)
  def log(str: String): IO[Unit]  = IO.suspend(println(s"writing message to file : $str"))
}
// test

object main extends App {
  import IO.{ log => logIO }
  import SimpleWriter._
  import Monad._

  def add(a: Double, b: Double): SimpleWriter[Double] =
    for {
      _ <- log(s"add $a to $b ")
      res = a + b
      _ <- log(s"the result of the operation is $res")
    } yield res

  def add2(a: Double, b: Double): IO[Double] =
    for {
      _ <- logIO(s"Adding $a to $b ")
      res = a + b
      _ <- logIO(s"the result of the operation is $res")
    } yield res

  def add3[F[_]](a: Double, b: Double)(implicit m: Monad[F]): F[Double] =
    ???
  println(add(3, 23))
  println(add2(3, 23))
}
