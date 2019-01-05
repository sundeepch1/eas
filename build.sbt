name := """my-first-app"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

// https://mvnrepository.com/artifact/javax.activation/activation
libraryDependencies += "javax.activation" % "activation" % "1.1.1"


// https://mvnrepository.com/artifact/javax.mail/mail
libraryDependencies += "javax.mail" % "mail" % "1.4"


// https://mvnrepository.com/artifact/mysql/mysql-connector-java
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.41"

// https://mvnrepository.com/artifact/org.hibernate/hibernate-entitymanager
libraryDependencies += "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final"

// https://mvnrepository.com/artifact/org.hibernate.javax.persistence/hibernate-jpa-2.1-api
libraryDependencies += "org.hibernate.javax.persistence" % "hibernate-jpa-2.1-api" % "1.0.2.Final"


// https://mvnrepository.com/artifact/org.codehaus.jackson/jackson-mapper-asl
libraryDependencies += "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.13"

// https://mvnrepository.com/artifact/org.codehaus.jackson/jackson-core-asl
libraryDependencies += "org.codehaus.jackson" % "jackson-core-asl" % "1.9.13"