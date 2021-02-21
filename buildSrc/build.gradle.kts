repositories {
	jcenter()
	mavenCentral()
}

plugins {
	`kotlin-dsl`
}

dependencies {
	implementation("com.hierynomus:sshj:0.31.0")
	implementation("me.tongfei:progressbar:0.9.0")
	implementation("com.zaxxer:HikariCP:4.0.2")
	implementation("org.postgresql:postgresql:42.2.19")
	implementation("org.flywaydb:flyway-core:7.5.3")
}
