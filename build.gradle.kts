import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    id("jacoco")

    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.21"
    kotlin("plugin.jpa") version "1.9.21"
}

group = "com.mikes"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

jacoco {
    toolVersion = "0.8.11"
}

repositories {
    mavenCentral()
}

val jacksonDataTypeVersion: String by project
val postgresqlVersion: String by project
val hikariVersion: String by project
val mockkVersion: String by project

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.1.0"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-sns")
    implementation("io.awspring.cloud:spring-cloud-aws-starter-sqs")
    implementation("io.awspring.cloud:spring-cloud-aws-starter")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonDataTypeVersion")

    runtimeOnly("org.postgresql:postgresql:$postgresqlVersion")
    runtimeOnly("com.zaxxer:HikariCP:$hikariVersion")

    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation(platform("io.cucumber:cucumber-bom:7.15.0"))
    testImplementation("io.cucumber:cucumber-java")
    testImplementation("io.cucumber:cucumber-junit-platform-engine")
    testImplementation("org.junit.platform:junit-platform-suite")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.mockk:mockk:$mockkVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.bootJar {
    archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")
}

tasks.withType<Test> {
    useJUnitPlatform()
    systemProperty("cucumber.junit-platform.naming-strategy", "long")
    finalizedBy("jacocoTestReport")
}

tasks.jacocoTestReport {
    dependsOn("test")
    reports {
        html.required = true
        xml.required = true
    }
}
