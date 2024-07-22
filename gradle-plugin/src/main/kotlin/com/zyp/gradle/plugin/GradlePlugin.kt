package com.zyp.gradle.plugin

import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.services.BuildService
import org.gradle.api.tasks.TaskState
import java.util.TreeSet

class GradlePlugin : Plugin<Project> {

    private val taskMap = HashMap<String, TaskExecTimeInfo>()
    private val taskPathList = ArrayList<String>()
    private var startBuildTime = 0L

    override fun apply(project: Project) {
        println("GradlePlugin apply")
        if (startBuildTime == 0L) {
            startBuildTime = System.currentTimeMillis()
        }
        //创建一个 Extension，配置输出结果
        val timeCostExt =
            project.extensions.create("taskExecTime", BuildTimeCostExtension::class.java)

        // 监听每个task的执行
        project.gradle.addListener(object : TaskExecutionListener {
            override fun beforeExecute(task: Task) {
                val taskExecTimeInfo = TaskExecTimeInfo(
                    startTime = System.currentTimeMillis(), path = task.path
                )
                taskMap[task.path] = taskExecTimeInfo
                taskPathList.add(task.path)
            }

            override fun afterExecute(task: Task, state: TaskState) {
                taskMap[task.path]?.apply {
                    endTime = System.currentTimeMillis()
                    execTime = endTime - startTime
                }
            }
        })

        project.gradle.addBuildListener(object : BuildListener {
            override fun settingsEvaluated(settings: Settings) {
            }

            override fun projectsLoaded(gradle: Gradle) {
            }

            override fun projectsEvaluated(gradle: Gradle) {
            }

            override fun buildFinished(result: BuildResult) {
                println("------------------- build execute time ----------------------------")
                if (timeCostExt.sorted) {
                    // 使用 TreeSet 按照执行时长排序
                    val taskSet = TreeSet<TaskExecTimeInfo>()
                    taskMap.values.forEach {
                        taskSet.add(it)
                    }
                    taskSet.forEach {
                        val path = it.path
                        val execTime = taskMap[path]?.execTime ?: 0L
                        if (execTime >= timeCostExt.threshold) {
                            println("$path  [${execTime}ms]")
                        }
                    }
                } else {
                    // 按照task的执行顺序打印出执行时长
                    taskPathList.forEach { path ->
                        val execTime = taskMap[path]?.execTime ?: 0L
                        if (execTime >= timeCostExt.threshold) {
                            println("$path  [${execTime}ms]")
                        }
                    }
                }
                println("-------------------build total execute time: [${System.currentTimeMillis() - startBuildTime} ms] ----------------------------")
            }

        })
    }

}

open class BuildTimeCostExtension {
    //task执行时间超过该值才会统计
    var threshold: Int = 0

    //是否按照task执行时长进行排序，true-表示从大到小进行排序，false-表示不排序
    var sorted = false

}

class TaskExecTimeInfo : Comparable<TaskExecTimeInfo> {
    var startTime: Long = 0L
    var path: String = ""
    var endTime: Long = 0L
    var execTime: Long = 0L

    constructor(startTime: Long, path: String) {
        this.startTime = startTime
        this.path = path
    }

    override fun compareTo(other: TaskExecTimeInfo): Int {
        return other.execTime.compareTo(this.execTime)
    }

}

