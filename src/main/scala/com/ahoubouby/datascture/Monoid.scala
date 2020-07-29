package com.ahoubouby.datascture

trait Monoid[A] {
  def zero: A
  def append(f: A, f2: => A): A
}
