val seleniumJavaVersion = "4.14.1"
val seleniumJupiterVersion = "5.0.1"
val webdrivermanagerVersion = "5.6.3"
val mockitoAgent = configurations.create("mockitoAgent")
val mockitoVersion = "5.14.2"

plugins {
    java
    jacoco
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.sonarqube") version "7.2.2.6593"
}

group = "id.ac.ui.cs.advprog"
version = "0.0.1-SNAPSHOT"
description = "eshop"

sonar {
    properties {
        property("sonar.projectKey", "A-Christopher-Evan-T-2406358056_Modul-1-Coding-Standards")
        property("sonar.organization", "a-christopher-evan-t-2406358056")
        property("sonar.sources", "src/main/java,src/main/resources/templates")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}


configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-thymeleaf-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.seleniumhq.selenium:selenium-java:${seleniumJavaVersion}")
    testImplementation("io.github.bonigarcia:selenium-jupiter:${seleniumJupiterVersion}")
    testImplementation("io.github.bonigarcia:webdrivermanager:${webdrivermanagerVersion}")
    testImplementation("org.mockito:mockito-core:${mockitoVersion}")
    mockitoAgent("org.mockito:mockito-core:${mockitoVersion}") { isTransitive = false }
}

tasks.register<Test>("unitTest") {
    description = "Runs unit tests."
    group = "verification"
    filter {
        excludeTestsMatching("*FunctionalTest")
    }
}

tasks.register<Test>("functionalTest") {
    description = "Runs functional tests."
    group = "verification"
    filter {
        includeTestsMatching("*FunctionalTest")
    }
    systemProperty("wdm.chromeDriverVersion", "144")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    jvmArgs("-javaagent:${mockitoAgent.asPath}")
    if (testClassesDirs.isEmpty) {
        testClassesDirs = sourceSets["test"].output.classesDirs
        classpath = sourceSets["test"].runtimeClasspath
    }
}

tasks.test {
    filter {
        excludeTestsMatching("*FunctionalTest")
    }

    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}