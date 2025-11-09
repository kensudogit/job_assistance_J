import org.gradle.api.tasks.Exec

plugins {
    java
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.jobassistance"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
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
    // Spring Boot Web
    implementation("org.springframework.boot:spring-boot-starter-web")
    
    // Spring Boot Data JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    
    // PostgreSQL Driver
    runtimeOnly("org.postgresql:postgresql")
    
    // Spring Boot Validation
    implementation("org.springframework.boot:spring-boot-starter-validation")
    
    // Spring Boot Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    
    // Spring Boot WebSocket
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    
    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    
    // Jackson for JSON
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    
    // Spring Boot DevTools
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    
    // Spring Boot Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    
    // Swagger/OpenAPI Documentation
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

springBoot {
    mainClass.set("com.jobassistance.JobAssistanceApplication")
}

// Node.jsタスク
tasks.register<Exec>("npmInstall") {
    description = "Install npm dependencies"
    group = "node"
    workingDir = projectDir
    commandLine("npm", "install")
}

tasks.register<Exec>("npmBuild") {
    description = "Build Next.js application"
    group = "node"
    workingDir = projectDir
    commandLine("npm", "run", "build")
    dependsOn("npmInstall")
}

tasks.register<Exec>("npmDev") {
    description = "Run Next.js development server"
    group = "node"
    workingDir = projectDir
    commandLine("npm", "run", "dev")
    dependsOn("npmInstall")
}

tasks.register<Exec>("npmTest") {
    description = "Run tests with Vitest"
    group = "node"
    workingDir = projectDir
    commandLine("npm", "run", "test")
    dependsOn("npmInstall")
}

// 統合タスク
tasks.register("setup") {
    description = "Setup project (install all dependencies)"
    group = "setup"
    dependsOn("npmInstall")
}

tasks.register("buildAll") {
    description = "Build entire project"
    group = "build"
    dependsOn("build", "npmBuild")
}

tasks.register("run") {
    description = "Run both frontend and backend"
    group = "application"
    dependsOn("bootRun", "npmDev")
}

// デフォルトタスク
defaultTasks("setup")
