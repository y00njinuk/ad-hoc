
import io.circe.{Json, parser => circeMethods}
import org.json4s
import org.json4s.JArray
import org.json4s.jackson.{JsonMethods => json4sMethods}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class Json4sWithCirceUseTest extends AnyFunSuite with Matchers {
  test("json4s -> circe") {
    val jsonScott = json4sMethods.parse(""" {"name":"Scott", "age":33} """)
    val jsonAlice = json4sMethods.parse(""" {"name":"Alice", "age":31} """)

    import json4sMethods._
    val jValue: json4s.JValue = JArray(List.empty).++(jsonScott).++(jsonAlice)
    val rawJson = compact(render(jValue))

    rawJson should equal("""[{"name":"Scott","age":33},{"name":"Alice","age":31}]""")

    case class Lecture(title: String, price: Int, students: List[Student])
    case class Student(name: String, age: Int)

    val lecture = Lecture("Math", 100, List.empty)

    val json = circeMethods.parse(rawJson)

    println(json.getOrElse(Json.Null))
  }
}