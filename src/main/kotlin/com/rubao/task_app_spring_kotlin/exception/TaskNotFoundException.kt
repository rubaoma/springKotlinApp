package com.rubao.task_app_spring_kotlin.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
data class TaskNotFoundException(override val message: String): RuntimeException()
