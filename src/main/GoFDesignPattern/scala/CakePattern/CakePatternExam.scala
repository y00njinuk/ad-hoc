package GoFDesignPattern.scala.CakePattern

object CakePatternExam extends UserServiceComponent with UserRepositoryComponent {
  val userRepository = new UserRepository
  val userService = new UserService

  def main(args: Array[String]): Unit = {
    val userService = CakePatternExam.userService
    val user = userService.authenticate("id", "password")
  }
}
