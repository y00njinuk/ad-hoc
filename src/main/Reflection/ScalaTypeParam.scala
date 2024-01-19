package Reflection
import scala.reflect.runtime.universe._

/**
 * @see https://docs.scala-lang.org/overviews/reflection/typetags-manifests.html
 */
object ScalaTypeParam {
  def paramInfo[T](x: T)(implicit tag: TypeTag[T]): Unit = {
    val targs = tag.tpe match { case TypeRef(_, _, args) => args }
    println(s"type of $x has type arguments $targs")
  }

  def paramInfoSimple[T: TypeTag](x: T): Unit = {
    val targs = typeOf[T] match { case TypeRef(_, _, args) => args }
    println(s"type of $x has type arguments $targs")
  }

  def weakParamInfo[T](x: T)(implicit tag: WeakTypeTag[T]): Unit = {
    val targs = tag.tpe match { case TypeRef(_, _, args) => args }
    println(s"type of $x has type arguments $targs")
  }

  def main(args: Array[String]): Unit = {
    paramInfo(42) // type of 42 has type arguments List()
    paramInfo(List(1,2)) // type of List(1, 2) has type arguments List(Int)

    paramInfoSimple(42) // type of 42 has type arguments List()
    paramInfoSimple(List(1,2)) // type of List(1, 2) has type arguments List(Int)

    weakParamInfo(List[Int]()) // type of List() has type arguments List(Int)
    def foo[T] = weakParamInfo(List[T]()) // type of List() has type arguments List(T)
    foo[Int]
  }
}