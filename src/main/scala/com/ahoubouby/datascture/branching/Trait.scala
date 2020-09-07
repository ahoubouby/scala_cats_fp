package com.ahoubouby.datascture.branching
import cats.Functor

sealed trait Tree[+A]

final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

final case class Leaf[A](value: A) extends Tree[A]

object Tree {
  val leaf: Tree[Int] = Leaf(100)

  def branch(x: Int, y: Int): Tree[Int] = {
    Branch(Leaf(x), Leaf(y))
  }
}

object branching extends App {

  import cats.Functor
  import Tree._
  implicit val treeFunctor: Functor[Tree] = new Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] = fa match {
      case Leaf(value)         => Leaf(f(value))
      case Branch(left, right) => Branch(map(left)(f), map(right)(f))
    }
  }

  println(treeFunctor.map(Tree.branch(33, 33))(x => x + 22))
}
