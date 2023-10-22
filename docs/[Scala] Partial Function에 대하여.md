# 개요
우리가 일반적으로 알고 있는 함수의 형태는 완전 함수(total function)이다.
e.g. double 함수가 처리하지 못하는 입력값 x는 존재하지 않는다.
```scala
def double(x: Int) = x * 2
```
- partial function 이란 정의되어 있는 데이터의 하위 집합(subset)으로만 적용 가능한 함수를 의미한다.
  - e.g. 입력값의 제곱근을 반환하는 함수는 입력값이 음수이여선 안된다.
  - e.g. 입력값으로 나누는 함수는 입력값이 0이면 안된다. 
- 즉, 입력 데이터 중 일부에만 적용 가능한 함수를 의미한다.
- 스칼라에서 정의한 partial function은 case 패턴을 함수의 입력값에 적용하는 함수 리터럴로서 입력값이 주어진 패턴 중 최소 하나는 일치할 것을 요구한다. 만족하는 패턴이 없을 경우에는 에러를 발생시킨다.

## 정의 및 소개
- 스칼라 문서(Scaladoc)에서 정의한 partial function의 특징은 다음과 같다.
  - 단항 연산자이다. 즉, 하나의 피연산자만을 가진다.
  - 값의 서브 도메인으로 적용이 가능하다.
  - 도메인을 정의하기 위해 암시적(implicitly)으로 isDefinedAt와 apply 메서드를 포함할 수 있다.
- 예시
```scala
val squareRoot: PartialFunction[Double, Double] = { case x if x >= 0 => Math.sqrt(x) }
```
- 위 예시에 소개된 squreRoot 함수의 특징은 다음과 같다.
  - Double 타입의 단일 파라미터를 가진다.
  - x >= 0 조건과 같은 Double 타입의 서브 도메인을 적용하였다.
  - isDefinedAt 메서드와 apply 메서드들이 암시적으로 정의되어 있다.
- isDefinedAt 메서드를 활용하면 입력 값에 대해 결과 값을 정의하고 있는지 미리 확인이 가능하다.
```
scala> squareRoot.isDefinedAt(4)
res1: Boolean = true

scala> squareRoot.isDefinedAt(-2)
res2: Boolean = false
```

## 그렇다면 언제 사용하면 될까?
- 체이닝 기법을 사용한다면 partial fucntion을 유용하게 활용할 수 있다.
- 양수를 음수로 바꾸거나 또는 반대로 바꾸는 기능을 원할 때 체이닝 기법을 활용하여 아래와 같이 구현이 가능하다.
  - 만약, 해당 숫자가 음수거나 0이면 절대값을 반환한다.
  - 만약, 해당 숫자가 양수이면 -1을 곱하여 반환한다.
  - orElse라는 체이닝 연산자를 활용하여 위의 partial function들을 활용할 수 있다.
  - PartialFunction 트레이트에서는 andThen이라는 체이닝 연산자도 포함하고 있다.
```scala
scala> val negativeOrZeroToPositive: PartialFunction[Int, Int] = { case x if x <= 0 => Math.abs(x) }
negativeOrZeroToPositive: PartialFunction[Int,Int] = <function1>

scala> val positiveToNegative: PartialFunction[Int, Int] = { case x if x > 0 => -1 * x }
positiveToNegative: PartialFunction[Int,Int] = <function1>

scala> val swapSign: PartialFunction[Int, Int] = { positiveToNegative orElse negativeOrZeroToPositive }
swapSign: PartialFunction[Int,Int] = <function1>
```
- 컬렉션에서 map, filter, collect 등의 연산을 사용할 때도 partial function을 유용하게 활용할 수 있다.
```scala
scala> val parseRange: PartialFunction[Any, Int] = { case x: Int if x > 10 => x+1 }
parseRange: PartialFunction[Any,Int] = <function1>

scala> List(15, 3, "aString") collect { parseRange }
res12: List[Int] = List(16)
```
- List(15, 3, "aString")에서 parseRange의 case 구문에 해당되는 원소들만 partial function의 입력값으로 사용된다.
- parseRange는 map 함수에도 적용하여 사용할 수 있다. 하지만 map 함수는 모든 요소들을 대상으로 수행이 되기 때문에 3과 1의 요소에 대해서 MatchError가 발생하게 된다.
- 아래와 같이 anonymous function을 partial function으로 활용하여도 문제가 없다.
```
scala> List(1, 2) collect { case i: Int => i > 10 }
res14: List[Boolean] = List(false, false)

scala> List(1, 2) filter { case i: Int => i > 10 }
res15: List[Int] = List()
```

# 참고 및 출처
- 러닝 스칼라 책 (p.89)
- Programming In Scala (Chatper 15 p.337 ~ 339)
- https://www.baeldung.com/scala/partial-functions
