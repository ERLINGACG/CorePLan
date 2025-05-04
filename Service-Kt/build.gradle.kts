plugins {
    kotlin("jvm")
    application
}

group = "com.erling"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":Pojo-J"))
    implementation(project(":Dao-J"))

    testImplementation(kotlin("test"))
}

application {
    mainClass.set("com.erling.Application")
}
tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}