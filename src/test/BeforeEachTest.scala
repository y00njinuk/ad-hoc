import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class BeforeEachTest extends AnyFlatSpec {
  def printOne() = { println("one") }
  def printTwo() = { println("two") }

  it should "call printTwo() method regardless of result of test" in {
    try {
      printOne()
      (1+1) shouldBe 3
    } finally {
      printTwo()
    }
  }
}
