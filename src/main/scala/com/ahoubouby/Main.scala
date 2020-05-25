package com.ahoubouby

import com.ahoubouby.typeclass_.{ Branch, FoldableTree, Leaf, Monoid }

object Main extends App {
  // implement of FoldableTree
  val tree = Branch(Leaf(3), Branch(Leaf(2), Leaf(5)))
  val productMonoid = new Monoid[Int] {
    override def zero: Int                     = 1
    override def op(ele1: Int, ele2: Int): Int = ele1 * ele2
  }

  // implement of  FoldableOption

  val option = Some(3)

  val resultFolding     = FoldableTree.foldRight(tree)(0)(_ + _)
  val resultLeftFolding = FoldableTree.foldLeft(tree)(0)(_ + _)
  val resultMapFolding  = FoldableTree.foldMap(tree)(identity)(productMonoid)
  println(resultFolding)
  println(resultLeftFolding)
  println(resultMapFolding)

}
