package edu.parsing

import edu.parsing.repository.CsvRepository
import edu.parsing.service.SubprocessNotifyService
import edu.parsing.service.TaskService

// Started with chrono
fun main() {
    val notificationService = SubprocessNotifyService()
    val taskRepository = CsvRepository("tasks.csv")
    val taskService = TaskService(taskRepository, notificationService)

    taskService.checkUpdates()
}