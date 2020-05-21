package com.ahoubouby.typeclass_

case class Person(name: String, email: String)

object Person {
  implicit val jStringWriter: JsonWriter[String] = (value: String) =>
    JString(value)

  implicit val jObjectPerson: JsonWriter[Person] = (value: Person) =>
    JObject(Map("name" -> JString(value.name), "email" -> JString(value.email)))
}
