package com.ahoubouby.typeclass_

trait Json

case class JString(get: String) extends Json
case class JNumber(get: Double) extends Json
case class JObject(get: Map[String, Json]) extends Json
case object JNull extends Json

// json writer is our type class
trait JsonWriter[A] {
  def write(value: A): Json
  //def read(json: Json): A
}

// interface objects
object Json {
  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json = w.write(value)
}
