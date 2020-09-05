package com.ahoubouby.datascture

trait Monoid[A] {
  def zero: A
  def append(a: A, b: => A): A
}
