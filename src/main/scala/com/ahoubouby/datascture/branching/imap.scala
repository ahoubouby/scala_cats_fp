package com.ahoubouby.datascture.branching

trait Codec[A] {
  self =>
  def encode(value: A): String
  def decode(value: String): A
  def imap[B](dec: A => B, enc: B => A): Codec[B] = {
    new Codec[B] {
      override def encode(value: B): String = self.encode(enc(value))

      override def decode(value: String): B = dec(self.decode(value))
    }
  }
}

object imap extends App {

  implicit val doubleCodec: Codec[Double] = new Codec[Double] {
    override def encode(value: Double): String = value.toString

    override def decode(value: String): Double = value.toDouble
  }
  implicit val stringCodec: Codec[String] = new Codec[String] {
    override def encode(value: String): String = value

    override def decode(value: String): String = value
  }

  implicit val intCodec: Codec[Int] = stringCodec.imap(_.toInt, _.toString)
  implicit def boxCodec[A](implicit c: Codec[A]): Codec[Box[A]] =
    c.imap(Box(_), _.value)

  val box: Box[String] = Box("string inside box")
  println(boxCodec[String].decode("string come from outside"))
}
