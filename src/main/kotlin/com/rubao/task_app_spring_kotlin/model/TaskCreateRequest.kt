package com.rubao.task_app_spring_kotlin.model

import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class TaskCreateRequest(
        @NotBlank(message = "description can't be empty")
        val description: String,
        val isReminderSet: Boolean,
        val isTaskOpen: Boolean,
        @NotBlank(message = "created_on can't be empty")
        val createdOn: LocalDateTime,
        val priority: Priority
)
