
import Echo.{Lecture, Student}
import io.circe.Json
import io.circe.syntax._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import java.nio.ByteBuffer

class ProtobufSerializerTest extends AnyFunSuite with Matchers {
  test("scalapb") {
    val students = List(Student("Scott", 33), Student("Alice", 31))
    val lecture = Lecture("Math", 100, students)
    val serializer = ProtobufSerializer(Lecture)

    // 1. class -> protocol buffer byte stream
    val bytes: Array[Byte] = serializer.serialize(lecture)

    // 2. protocol buffer byte stream -> class
    val nLecture: Lecture = serializer.deserialize(bytes)

    lecture should equal(nLecture)

    val jObj: Json = serializer.toJsonByCirce(ByteBuffer.wrap(bytes))
    val newjObj: Json = Json.arr()

    val res = newjObj.deepMerge(jObj)
    println(res)
  }
}