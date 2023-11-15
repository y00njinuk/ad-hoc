import com.twitter.util.{Await, Future, Return, Throw}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class TwitterFutureTest extends AnyFlatSpec {
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

  it should "Future.never is never non-terminated" in {
    val fList: Seq[Future[Any]] = List(
      Future.never,
      Future.value(1),
      Future.value("abc")
    )

    Await.result(
      Future
        .collectToTry(fList) // blocking forever. because of Future.never
        .map { results: Seq[Any] =>
          results foreach {
            case Return(_) => println("Return")
            case Throw(_) => println("throw")
          }
        }
    )
  }

  it should "flatMap return exception and catch" in {
    // type casting Future[Nothing] -> Future[Int]
    val f: Future[Int] = Future.exception(new RuntimeException("Boom!"))

    val newf: Future[Int] = f.flatMap { x =>
      println("I'm being executed")
      Future.value(x+10)
    }

    assertThrows[RuntimeException] (Await.result(newf))
  }

  it should "filter+map exclude Future.exception in result" in {
    val fList: Seq[Future[Int]] = List(
      Future.value(1),
      Future.value(2),
      Future.exception(new RuntimeException())
    )

    val res = fList
      .filter(_.poll.isDefined)                 // Keep only the defined (completed) Futures
      .flatMap(_.poll)                          // Extract the Option[Try[Int]] from the Future
      .collect { case Return(value) => value }  // Collect only the successfully completed values

    res.mkString(",") shouldBe "1,2"
  }
}
