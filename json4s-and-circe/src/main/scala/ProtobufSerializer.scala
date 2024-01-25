
import io.circe.Json
import org.json4s.JsonAST.JValue
import scalapb.json4s.{JsonFormat => json4s}
import scalapb.{GeneratedMessage, GeneratedMessageCompanion}
import scalapb_circe.{JsonFormat => circe}

import java.nio.ByteBuffer

class ProtobufSerializer[T <: GeneratedMessage](s: GeneratedMessageCompanion[T]) {
  def serialize(obj: T): Array[Byte] = s.toByteArray(obj)
  def deserialize(bytes: Array[Byte]): T = s.parseFrom(bytes)
  def deserialize(bBuffer: ByteBuffer): T = deserialize(bBuffer.array())

  /**
   * Protocol Buffer 포맷의 바이트 스트림을 JSON 객체로 변환한다.
   * @param bBuffer
   * @return
   */
  def toJson(bBuffer: ByteBuffer): JValue = json4s.toJson(deserialize(bBuffer))
  def toJsonByCirce(bBuffer: ByteBuffer): Json = circe.toJson(deserialize(bBuffer))

  /**
   * Protocol Buffer 포맷의 바이트 스트림을 JSON 문자열로 변환한다.
   *
   * @param bBuffer byte array encoded of Protocol Buffer
   * @return json string
   */
  def toJsonString(bBuffer: ByteBuffer): String = json4s.toJsonString(deserialize(bBuffer))
}

object ProtobufSerializer {
  def apply[T <: GeneratedMessage](s: GeneratedMessageCompanion[T]): ProtobufSerializer[T] =
    new ProtobufSerializer(s)
}