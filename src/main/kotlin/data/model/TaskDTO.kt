package data.model

import java.time.LocalDateTime

data class TaskDTO(
        val id: Long,
        val description: String,
        val isReminderSet: Boolean,
        val isTaskOpen: Boolean,
        val createdOn: LocalDateTime,
        val priority: Priority
)
