plugins {
    id("java-library")
    id("groovy")
    `maven-publish`
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


dependencies {
//    implementation fileTree(include: ['*.jar'], dir: 'libs')
//    implementation project(':matrix-commons')
//    implementation project(':matrix-arscutil')

    implementation(gradleApi())
    implementation(localGroovy())
    implementation("org.ow2.asm:asm:7.0")
    implementation("org.ow2.asm:asm-commons:7.0")
    implementation("com.android.tools.build:gradle:8.5.0")
}

/**
 * 避免配置： 延迟任务配置直到任务实际执行，从而提高构建性能。
 * 类型安全配置： 允许使用泛型指定任务类型，确保类型安全并防止配置错误。
 * 灵活定制： 支持各种配置选项，包括任务操作、依赖项和属性。
 */
tasks.register("A1") {// 配置阶段不执行
    doFirst {// 执行阶段执行
        println("A1 doFirst execute")
    }
    // 配置阶段即执行
    println("A1 execute components.names: " + components.names)
    doLast {// 执行阶段执行
        println("A1 doLast execute")
    }
}

/**
 * 直接创建任务： 立即创建并配置任务。
 * 简单语法： 为基本任务定义提供更简单的语法。
 * 向后兼容性： 维护与较旧 Gradle 版本的兼容性。
 */
task("A2") {
    println("A2 execute")
}

project.task("A3") {
    println("A3 execute")
}

project.tasks.create("A4") {
    //设置inputs
    inputs.property("name", "hjy")
    inputs.property("age", 20)


    val outputFile = File(layout.buildDirectory.get().asFile, "test.txt")
    outputFile.createNewFile()

    //设置outputs
    outputs.file(outputFile.path)
    println("output File: ${layout.buildDirectory.get().asFile.path}")

    println("A4 execute")
}

open class SayHelloTask : DefaultTask() {
    //定义输入
    @Input
    var username: String? = null

    @Input
    var age: Int = 0

    //定义输出
    @OutputDirectory
    var destDir: File? = null

    @OutputFile
    var destFile: File? = null

    @TaskAction
    fun sayHello() {
        println("Hello $username ! age is $age")
    }
}

tasks.register<SayHelloTask>("A5") {
    println("A5 execute")
    age = 18
    username = "hjy"
    destDir = file("${layout.buildDirectory.get().asFile.path}/test")
    destFile = file("${layout.buildDirectory.get().asFile.path}/aaa.txt")
}

project.tasks.create<SayHelloTask>("A6") {
    println("A6 execute")
    age = 28
    username = "zyp"
    destDir = file("${layout.buildDirectory.get().asFile.path}/test2")
    destFile = file("${layout.buildDirectory.get().asFile.path}/aaa2.txt")
}

project.beforeEvaluate {
    println("--- Setting start---")
}

project.afterEvaluate {
    println("--- Setting end ---")
}


publishing {
    repositories {
        maven {
            url = uri("../repo")
//            credentials {
//                username = "your-username"
//                password = "your-password"
//            }
        }
    }

    publications {
//        register<MavenPublication>("release") {
//            groupId = "com.example"
//            artifactId = "my-library"
//            version = "1.0.0"
//
//            from(components.getByName("release"))
//        }
        create<MavenPublication>("maven") {
//            groupId = "com.zyp.gradle.plugin"
//            artifactId = "gradle-plugin"
//            version = "1.0.0"

            artifacts {
                groupId = "com.zyp.gradle.plugin"
                artifactId = "gradle-plugin"
                version = "1.0.1"
            }

            println("publications components.names: " + components.names)
            from(components["java"])
        }
    }
}

