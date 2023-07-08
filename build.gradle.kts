import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
	id("org.springframework.boot") version "2.7.1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "nl.anwb"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral {
		content {
			excludeModule("javax.media", "jai_core")
		}
	}
	maven { url = URI("https://repo.osgeo.org/repository/release/") }
}


dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("de.grundid.opendatalab:geojson-jackson:1.0")
	implementation("org.geotools:gt-main:24.0")
	implementation("org.geotools:gt-referencing:24.0")
	implementation("org.geotools:gt-geojson:24.0")
	implementation("org.geotools:gt-epsg-hsql:24.0")
	implementation("org.locationtech.jts:jts-core:1.19.0")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("com.natpryce:hamkrest:1.4.2.0")
	testImplementation("io.mockk:mockk:1.13.1")
	testImplementation("com.ninja-squad:springmockk:4.0.2")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
