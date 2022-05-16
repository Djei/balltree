import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

description = """
  Kotlin Balltree implementation by Djei
""".trimIndent()

plugins {
    kotlin("jvm") version "1.5.30"
}

group = "com.djei"
version = "0.0.1"
kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(11))
    }
}

repositories {
    mavenCentral()
}

val ktlint by configurations.creating {
    extendsFrom(configurations["implementation"])
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")

    // ktlint
    ktlint("com.pinterest:ktlint:0.45.0") {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val outputDir = "${project.buildDir}/reports/ktlint/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))
val ktlintCheck by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Check Kotlin code style."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("src/**/*.kt", "build.gradle.kts")
    jvmArgs("--add-opens", "java.base/java.lang=ALL-UNNAMED")
}
tasks.named("check") {
    dependsOn(ktlintCheck)
}
val ktlintFormat by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("-F", "src/**/*.kt", "build.gradle.kts")
    jvmArgs("--add-opens", "java.base/java.lang=ALL-UNNAMED")
}
