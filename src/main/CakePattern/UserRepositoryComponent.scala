package CakePattern

trait UserRepositoryComponent {
  val userRepository: UserRepository

  class UserRepository {
    def authenticate(name: String, password: String): User = {
      println("authenticating user: " + name + password)
      User(name, password)
    }
    def create(user: User) = println("creating user: " + user)
    def delete(user: User) = println("deleting user: " + user)
  }
}
