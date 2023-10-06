package com.rubao.task_app_spring_kotlin.service

import com.rubao.task_app_spring_kotlin.data.Task
import com.rubao.task_app_spring_kotlin.data.model.TaskCreateRequest
import com.rubao.task_app_spring_kotlin.data.model.TaskDTO
import com.rubao.task_app_spring_kotlin.data.model.TaskUpdateRequest
import com.rubao.task_app_spring_kotlin.exception.BadRequestException
import com.rubao.task_app_spring_kotlin.exception.TaskNotFoundException
import org.springframework.stereotype.Service
import org.springframework.util.ReflectionUtils
import com.rubao.task_app_spring_kotlin.repository.TaskRepository
import java.lang.reflect.Field
import java.util.stream.Collectors
import kotlin.reflect.full.memberProperties

@Service
class TaskService(private val repository: TaskRepository) {

    private fun convertEntityToDto(task: Task): TaskDTO {
        return TaskDTO(
                task.id,
                task.description,
                task.isReminderSet,
                task.isTaskOpen,
                task.createdOn,
                task.priority
        )
    }
    private fun assignValuesToEntity(task: Task, taskRequest: TaskCreateRequest) {
        task.description = taskRequest.description
        task.isReminderSet = taskRequest.isReminderSet
        task.isTaskOpen = taskRequest.isTaskOpen
        task.createdOn = taskRequest.createdOn
        task.priority = taskRequest.priority
    }

    fun checkTaskId(id: Long) {
        if (repository.existsById(id).not()) {
            throw TaskNotFoundException("Task with ID: $id does not exist!")
        }
    }

    fun getAllTasks(): List<TaskDTO> =
            repository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList())

    fun getAllOpenTasks(): List<TaskDTO> =
            repository.queryAllOpenTasks().stream().map(this::convertEntityToDto).collect(Collectors.toList())

    fun getAllClosedTasks(): List<TaskDTO> =
            repository.queryAllClosedTasks().stream().map(this::convertEntityToDto).collect(Collectors.toList())

    fun getTaskById(id: Long): TaskDTO {
        checkTaskId(id)
        val task: Task = repository.findTaskById(id)
        return convertEntityToDto(task)
    }

    fun createTask(createRequest: TaskCreateRequest): TaskDTO {
        if(repository.doesDescriptionExist(createRequest.description)) {
            throw BadRequestException("There is already a task with description: ${createRequest.description}")
        }
        val task = Task()
        assignValuesToEntity(task, createRequest)
        val savedTask = repository.save(task)
        return convertEntityToDto(savedTask)
    }

    fun updateTask(id: Long, updateRequest: TaskUpdateRequest): TaskDTO {
        checkTaskId(id)
        val existingTask: Task = repository.findTaskById(id)

        for(prop in TaskUpdateRequest::class.memberProperties) {
            if (prop.get(updateRequest) != null) {
                val field: Field? = ReflectionUtils.findField(Task::class.java, prop.name)
                field?.let {
                    it.isAccessible = true
                    ReflectionUtils.setField(it, existingTask, prop.get(updateRequest))
                }
            }
        }
        val savedTask: Task = repository.save(existingTask)
        return convertEntityToDto(savedTask)
    }

    fun deleteTask (id: Long): String {
        checkTaskId(id)
        repository.deleteById(id)
        return  "Task with ID: $id has been deleted."
    }


}