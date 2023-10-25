package edu.parsing

import edu.parsing.repository.CsvRepository

// Started with chrono
fun main() {
    val notificationService = SubprocessNotifyService()
    val taskRepository = CsvRepository("tasks.csv")
    val taskService = TaskService(taskRepository, notificationService)

    taskService.checkUpdates()
}