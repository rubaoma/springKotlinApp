package com.rubao.task_app_spring_kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskAppSpringKotlinApplication

fun main(args: Array<String>) {
	runApplication<TaskAppSpringKotlinApplication>(*args)
	println("rubao code")
}
