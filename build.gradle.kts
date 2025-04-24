import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES
import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel

plugins {
    idea
    id("io.spring.dependency-management")
    id("org.springframework.boot") apply false
}

idea {
    project {
        languageLevel = IdeaLanguageLevel(21)
    }
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

allprojects {
    group = "ru.otus"

    repositories {
        mavenLocal()
        mavenCentral()
    }

    val guava: String by project
    val jsr305: String by project
    val sockjs: String by project
    val stomp: String by project
    val bootstrap: String by project
    val r2dbcPostgresql: String by project

    apply(plugin = "io.spring.dependency-management")
    dependencyManagement {
        dependencies {
            imports {
                mavenBom(BOM_COORDINATES)
            }
            dependency("com.google.guava:guava:$guava")
            dependency("com.google.code.findbugs:jsr305:$jsr305")
            dependency("org.webjars:sockjs-client:$sockjs")
            dependency("org.webjars:stomp-websocket:$stomp")
            dependency("org.webjars:bootstrap:$bootstrap")
            dependency("io.r2dbc:r2dbc-postgresql:$r2dbcPostgresql")
        }
    }
}

subprojects {
    plugins.apply(JavaPlugin::class.java)
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-Xlint:all,-serial,-processing"))
    }
}