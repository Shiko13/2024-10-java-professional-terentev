rootProject.name = "otusJava"
include("hw01-gradle")
include("hw02-collections-and-generics")
include("hw03-annotations")
include("hw04-garbage-collector")
include("hw05-byte-code")
include("hw06-oop")
include("hw07-patterns")
include("hw08-serialization")
include("hw10-jpql")
include("hw11-cache")
include("hw12-web-server")
include("hw13-di")
include("hw14-spring-boot")
include("hw15-executors")

pluginManagement {
    val dependencyManagement: String by settings
    val springframeworkBoot: String by settings
    val johnrengelmanShadow: String by settings

    plugins {
        id("io.spring.dependency-management") version dependencyManagement
        id("org.springframework.boot") version springframeworkBoot
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
    }
}
include("hw09-jdbc")
include("hw09-jdbc:demo")
findProject(":hw09-jdbc:demo")?.name = "demo"
include("hw09-jdbc:homework")
findProject(":hw09-jdbc:homework")?.name = "homework"
