import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.cloud.contract.verifier.config.TestFramework
import org.springframework.cloud.contract.verifier.plugin.ContractVerifierExtension

plugins {
    id("idea")
    id("groovy")
    id("maven-publish")
    id("org.springframework.cloud.contract") version "2.2.5.RELEASE"
    id("org.springframework.boot") version "2.4.1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
}

group = "pl.sii.bank"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
}

extra["springCloudVersion"] = "Hoxton.SR9"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-stream-rabbit")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.cloud:spring-cloud-starter-contract-verifier")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
        exclude(module = "mockito-junit-jupiter")
    }
    testImplementation("org.springframework.cloud:spring-cloud-stream-test-support")
    testImplementation("io.mockk:mockk:1.10.3")

    //SPOCK
    testImplementation("org.spockframework:spock-spring:1.3-groovy-2.5")
    testImplementation("dev.sarek:sarek-spock-extension:1.0-SNAPSHOT")
    testImplementation("dev.sarek:sarek-unfinal:1.0-SNAPSHOT")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

configure<ContractVerifierExtension> {
    setContractsDslDir(file("src/main/contracts"))
    setTestFramework(TestFramework.SPOCK)
    setBasePackageForTests("pl.sii.bank.accounting")
    setBaseClassMappings(
        mapOf(
            "web" to "pl.sii.bank.accounting.SpockWebContractBaseClass"
//            "messaging" to "pl.sii.bank.accounting.MessagingContractBaseClass"
        )
    )
    setFailOnInProgress(false)
}
