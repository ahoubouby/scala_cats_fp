package com.ahoubouby

object Main extends App {

  import com.ahoubouby.typeclass_.{Json, Person}
  import com.ahoubouby.typeclass_.Person._

  val person = Person(name = "abdelwahed", email = "ahoubouby@gmail.com")
  val writePersonJson = Json.toJson(person)
  println(writePersonJson)

}
