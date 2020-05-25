package com.ahoubouby

import com.ahoubouby.typeclass_.{ Branch, FoldableTree, Leaf, Monoid }

object Main extends App {
  // implement of FoldableTree
  val tree = Branch(Leaf(3), Branch(Leaf(2), Leaf(5)))

  def productMonoid[A, B](A: Monoid[A], B: Monoid[B]): Monoid[(A, B)] = new Monoid[(A, B)] {
    override def zero: (A, B) = (A.zero, B.zero)

    override def op(ele1: (A, B), ele2: (A, B)): (A, B) = (A.op(ele1._1, ele2._1), B.op(ele1._2, ele2._2))
  }

  val intMonoid = new Monoid[Int] {
    override def zero: Int = 0

    override def op(ele1: Int, ele2: Int): Int = ele1 + ele2
  }

  val stringMonoid = new Monoid[String] {
    override def zero: String = ""

    override def op(ele1: String, ele2: String): String = ele1 + ele2
  }

  val product = productMonoid(intMonoid, stringMonoid)
  // implement of  FoldableOption
  val option = Some(3)

  val resultFolding     = FoldableTree.foldRight(tree)(0)(_ + _)
  val resultLeftFolding = FoldableTree.foldLeft(tree)(0)(_ + _)
  val resultMapFolding  = FoldableTree.foldMap(tree)(el => (el, s"${el} ss -"))(product)
  println(resultFolding)
  println(resultLeftFolding)
  println(resultMapFolding)
  println(FoldableTree.toList(tree))

}
