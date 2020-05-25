package com.ahoubouby.typeclass_

trait Monoid[T] {
  def zero: T
  def op(ele1: T, ele2: T): T

}
