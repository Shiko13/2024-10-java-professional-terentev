rootProject.name = "otusJava"
include("hw01-gradle")
include("hw02-collections-and-generics")
include("hw03-annotations")
include("hw04-garbage-collector")
include("hw05-byte-code")
include("hw06-oop")
include("hw07-patterns")

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

