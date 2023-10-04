package com.rubao.task_app_spring_kotlin.model

data class TaskUpdateRequest(
        val description: String?,
        val isReminderSet: Boolean?,
        val isTaskOpen: Boolean?,
        val priority: Priority?
)
