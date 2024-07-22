package com.zyp.gradle.plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

class SayHelloTask : DefaultTask() {
    //定义输入
    @Input
    var username: String? = null

    @Input
    var age: Int = 0

    //定义输出
    @OutputDirectory
    var destDir: File? = null

    @TaskAction
    fun sayHello() {
        println("Hello $username ! age is $age")
    }
}
