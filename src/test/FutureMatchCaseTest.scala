import com.twitter.util.{Await, Future, Return, Throw}
import org.scalatest.flatspec.AnyFlatSpec

class FutureMatchCaseTest extends AnyFlatSpec {

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
}
