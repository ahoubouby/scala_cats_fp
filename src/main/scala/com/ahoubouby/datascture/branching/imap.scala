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
  def encode[A](value: A)(implicit codec: Codec[A]): String =
    codec.encode(value)
  def decode[A](value: String)(implicit codec: Codec[A]) = codec.decode(value)

  val box: Box[Int] = Box(232342)
  println(encode(box))
  // no exception management here
  println(decode[Box[String]]("23232"))
}
