package edu.parsing

import edu.parsing.repository.CsvRepository
import edu.parsing.repository.FileQueryRepository
import edu.parsing.service.SubprocessNotifyService
import edu.parsing.service.TaskService

// Started with chrono
fun main(args: Array<String>) {
    val cliArgs = try {
        args.toList()
            .chunked(2)
            .associate { it[0] to it[1] }
    } catch (_: Exception) {
        println("Error parsing command args: ${args.joinToString(separator = " ")}")
        return
    }

    val taskRepository = CsvRepository(cliArgs["--tasks"] ?: "tasks.csv")
    val queryRepository = FileQueryRepository(cliArgs["--queries"] ?: "queries.txt")
    val notificationService = SubprocessNotifyService()

    val taskService = TaskService(taskRepository, queryRepository, notificationService)

    while (true) {
        taskService.checkUpdates()
        Thread.sleep(1000L * (cliArgs["--timeout"]?.toInt() ?: 20))
    }
}
