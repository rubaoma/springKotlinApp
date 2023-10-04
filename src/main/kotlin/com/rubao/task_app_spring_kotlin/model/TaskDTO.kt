package com.rubao.task_app_spring_kotlin.model

import java.time.LocalDateTime

data class TaskDTO(
        val id: Long,
        val description: String,
        val isReminderSet: Boolean,
        val isTaskOpen: Boolean,
        val createdOn: LocalDateTime,
        val priority: Priority
)
