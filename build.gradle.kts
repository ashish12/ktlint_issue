@file:Suppress("PropertyName")


plugins {
    application
    kotlin("jvm") version "1.7.10"
}

group = "com.example"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
    }
}


repositories {
    mavenCentral()
}


val ktlint by configurations.creating

dependencies {
    ktlint("com.pinterest:ktlint:0.47.0")
}

tasks.named("check") {
    dependsOn("ktlintCheck")
}

val outputDir = "${project.buildDir}/reports/ktlint/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

val ktlintCheck by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Check Kotlin code style."
    classpath = ktlint
    setProperty("mainClass", "com.pinterest.ktlint.Main")
    args = listOf("src/**/*.kt")
}

val ktlintFormat by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    setProperty("mainClass", "com.pinterest.ktlint.Main")
    args = listOf("-F", "src/**/*.kt")
}