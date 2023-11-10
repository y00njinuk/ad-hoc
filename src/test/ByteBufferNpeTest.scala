import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import java.nio.ByteBuffer

class ByteBufferNpeTest extends AnyFlatSpec {

  it should "use Option instead of if-else statement" in {

    val bBufferExam1 = ByteBuffer.wrap(Array[Byte](1, 2, 3, 4))
    val bBufferExam2: ByteBuffer = null

    val optbBufferExam1 = Option(bBufferExam1)
    val optbBufferExam2 = Option(bBufferExam2)

    def res(bBuffer: Option[ByteBuffer]): String =
      bBuffer.map(_.array().map(_.toString).mkString(".")).getOrElse("null")

    res(optbBufferExam1) shouldBe "1.2.3.4"
    res(optbBufferExam2) shouldBe "null"
  }
}
