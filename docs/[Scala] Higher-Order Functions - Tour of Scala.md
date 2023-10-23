## 개요
- 고차함수(Higher-Order Functions)에 대해서 간단하게 정의하자면 다음과 같다.
- "함수를 파라미터로 정의하거나 함수의 반환 값으로 사용하는 패턴" 이다.
- 고차함수를 활용한다면  함수에 전달되는 인자(argument)의 상태 값이 노출되거나 변경될 수 있는 상황들을 없애준다.
- 고차함수를 활용하여 추상화 된 코드에 반복되는 코드를 적용함으로써 불필요한 중복을 줄일 수 있다.

## 예시
```scala
scala> val salaries = Seq(20000, 70000, 40000)
salaries: Seq[Int] = List(20000, 70000, 40000)

scala> val doubleSalary = (x: Int) => x * 2
doubleSalary: Int => Int = $$Lambda$1164/0x00000008007d9040@24a1858a

scala> val newSalaries = salaries.map(doubleSalary)
newSalaries: Seq[Int] = List(40000, 140000, 80000)
```
- 대표적인 예시로 스칼라 컬렉션에 map 함수가 있다. doubleSalary라는 람다를 정의하여 map 함수에 인자로 전달하였다.
- 타입 추론이 가능하므로 salaries.map(_ * 2) 와 같이 placeholder를 활용하여 더욱 간단하게 표현하는 것도 가능하다.

## 함수를 명시적으로 전달하는 것도 가능하다
- 함수를 정의하여 정의된 함수명을 전달하는 것도 가능하다.
- 아래의 예시를 통해 이해해보자.
```scala
scala> case class WeeklyWeatherForecast(temperatures: Seq[Double]) {
     |  private def convertCtoF(temp: Double) = temp * 1.8 + 32
     |  def forecastInFahrenheit: Seq[Double] = temperatures.map(convertCtoF)
     | }
defined class WeeklyWeatherForecast

scala> WeeklyWeatherForecast(Seq(1,2,3,4)).forecastInFahrenheit
res6: Seq[Double] = List(33.8, 35.6, 37.4, 39.2)
```
- forecastInFahrenheit 메소드는 convertCtoF 메소드를 명시적으로 map 함수의 인자로 할당하였다.

## 중복된 코드를 줄일 수 있다.
- 공통 파라미터와 반환값을 promotion 메소드로 통일하고 메소드별 동작을 고차함수를 활용하여 파라미터로 정의하였다.
- 이를 통해 반복된 코드는 하나의 함수로 통일화하고 개별 동작은 전달하는 함수의 구현체에 따라 다르게 정의할 수 있다.
```scala
scala> object SalaryRaiser {
  private def promotion(salaries: List[Double], promotionFunction: Double => Double): List[Double] =
    salaries.map(promotionFunction)

  def smallPromotion(salaries: List[Double]): List[Double] =
    promotion(salaries, salary => salary * 1.1)

  def greatPromotion(salaries: List[Double]): List[Double] =
    promotion(salaries, salary => salary * math.log(salary))

  def hugePromotion(salaries: List[Double]): List[Double] =
    promotion(salaries, salary => salary * salary)
}
defined object SalaryRaiser

scala> SalaryRaiser.smallPromotion(List(1,2,3,4))
res0: List[Double] = List(1.1, 2.2, 3.3000000000000003, 4.4)
```

## 함수를 반환하는 함수
- 고차함수를 활용하여 함수를 직접 정의하고 정의한 함수로 반환하는 경우도 있다.
- 특정한 용도로 사용하는 것은 아니고 필요에 맞게 사용하면 될 것으로 보인다.
- urlBuilder 메소드의 마지막 라인을 보면 String 타입의 파라미터를 2개 전달받아서 String 타입으로 반환하는 메소드를 정의하였다.
- 이를 통해 urlBuilder 메소드는 함수를 반환하는 타입이 된다.
```scala
scala> def urlBuilder(ssl: Boolean, domainName: String): (String, String) => String = {
  val schema = if (ssl) "https://" else "http://"
  (endpoint: String, query: String) => s"$schema$domainName/$endpoint?$query"
}
urlBuilder: (ssl: Boolean, domainName: String)(String, String) => String

scala> val domainName = "www.example.com"
domainName: String = www.example.com

scala> getURL: (String, String) => String
def getURL = urlBuilder(ssl=true, domainName)

scala> endpoint: String = users
val endpoint = "users"

scala> query: String = id=1
val query = "id=1"

scala> val url = getURL(endpoint, query) // "https://www.example.com/users?id=1": String
url: String = https://www.example.com/users?id=1
```

### 참고 및 출처
- https://docs.scala-lang.org/tour/higher-order-functions.html
