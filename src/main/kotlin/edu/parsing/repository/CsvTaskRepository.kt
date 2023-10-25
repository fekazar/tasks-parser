package edu.parsing.repository

import edu.parsing.model.Task
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

// todo: escape comas in header
fun Task.toCsvLine() = "${this.id},${this.header},${this.price}"

// Writes in format: id,header,price
class CsvRepository(val fileName: String) : TaskRepository {
    companion object {
        const val ID = "id"
        const val HEADER = "header"
        const val PRICE = "price"
    }

    private val csvWriter = PrintWriter(
        Files.newOutputStream(
        Path.of(fileName), StandardOpenOption.CREATE, StandardOpenOption.APPEND),
        false
    )

    private val tasks: MutableMap<Int, Task> = Files.readAllLines(Path.of(fileName))
        .asSequence()
        .drop(1) // Drop csv header
        .map { it.split(",") }
        .map { Task(it[0].toInt(), it[1], it[2].toIntOrNull()) }
        .groupingBy { it.id }
        .reduce { _, _, el -> el }
        .toMutableMap()

    override fun findById(id: Int): Task? = tasks[id]

    override fun save(task: Task): Task {
        if (tasks.containsKey(task.id))
            return task

        tasks[task.id] = task

        if (tasks.isEmpty())
            csvWriter.println("$ID,$HEADER,$PRICE")
        csvWriter.println(task.toCsvLine())

        csvWriter.flush()
        return task
    }
}
