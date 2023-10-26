package edu.parsing

import edu.parsing.repository.CsvRepository
import edu.parsing.repository.FileQueryRepository
import edu.parsing.service.SubprocessNotifyService
import edu.parsing.service.TaskService

// Started with chrono
fun main() {
    val taskRepository = CsvRepository("tasks.csv")
    val queryRepository = FileQueryRepository("queries.txt")
    val notificationService = SubprocessNotifyService()

    val taskService = TaskService(taskRepository, queryRepository, notificationService)
    taskService.checkUpdates()
}
