import com.twitter.util.{Await, Future, Return, Throw}
import org.scalatest.flatspec.AnyFlatSpec

class FutureCollectToTryTest extends AnyFlatSpec {
  it should "collectToTry return Return(_) or Throw(_)" in {
    val fList = List(
      Future.value(1),
      Future.value(2),
      Future.exception(new RuntimeException())
    )

    val res: Future[Seq[Int]] =
      Future.collectToTry(fList).map(_ map {
          case Return(value) => value
          case Throw(_) => 0
        })

    println(Await.result(res).mkString(","))
  }
}
