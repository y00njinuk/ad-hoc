namespace java schema // defines the package name for the generated code

struct Person {
	1: required string userName,
	2: optional i64 favoriteNumber,
	3: optional list<string> interests
}